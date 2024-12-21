package pl.edu.pw.ee.catering_backend.payment.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.OrderRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.UserRepository;
import pl.edu.pw.ee.catering_backend.orders.comms.OrderMapper;
import pl.edu.pw.ee.catering_backend.orders.domain.Order;
import pl.edu.pw.ee.catering_backend.orders.infrastructure.OrderStatus;
import pl.edu.pw.ee.catering_backend.user.domain.User;
import pl.edu.pw.ee.catering_backend.user.infrastructure.UserDbMapper;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Service
public class MockPaymentService implements IPaymentService {
    private final UserRepository userRepository;
    private final UserDbMapper userDbMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PasswordEncoder passwordEncoder;

    public MockPaymentService(
            UserRepository userRepository,
            UserDbMapper userDbMapper,
            OrderRepository orderRepository,
            OrderMapper orderMapper, PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userDbMapper = userDbMapper;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean payForOrder(
            UUID orderId,
            String login
    ) {
        final User user = userRepository
                .findByLogin(login)
                .map(userDbMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("User not found with login: " + login));

        final BigDecimal userCash = user.getWallet().getAmountOfMoney();
        final Order order = orderRepository
                .findById(orderId)
                .map(orderMapper::mapDbToDomainModel)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        if (!Objects.equals(order.getClient().getLogin(), login)) {
            throw new IllegalArgumentException("User with login: " + login + " is not the owner of the order with id: " + orderId + ".");
        }

        if (order.getStatus() != OrderStatus.UNPAID) {
            throw new IllegalArgumentException("Order needs to be in UNPAID status to be paid, order id: " + orderId + ".");
        }

        final BigDecimal newCash = getWalletBalanceAfterPayment(
                orderId,
                login,
                order,
                userCash
        );

        try {
            userRepository.updateWalletAmount(newCash, user.getLogin());
            orderRepository.updateOrderStatus(orderId, OrderStatus.PAID);
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating user wallet, user login:" + login + ", new cash: " + newCash + ".");
        }
    }

    @NotNull
    private static BigDecimal getWalletBalanceAfterPayment(
            UUID orderId,
            String login,
            Order order,
            BigDecimal userCash
    ) {
        final BigDecimal orderPrice = order.getTotalPrice();

        if (userCash.compareTo(orderPrice) < 0) {
            throw new IllegalArgumentException("User does not have enough money to pay for the order, user login: " +
                    login +
                    ", order id: " +
                    orderId +
                    ", user wallet: " +
                    userCash +
                    ", order price: " +
                    orderPrice + "."
            );
        }

        return userCash.subtract(orderPrice);
    }
}
