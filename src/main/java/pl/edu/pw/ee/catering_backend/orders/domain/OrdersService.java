package pl.edu.pw.ee.catering_backend.orders.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.OrderDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.OrderRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.UserRepository;
import pl.edu.pw.ee.catering_backend.offers.comms.MealMapper;
import pl.edu.pw.ee.catering_backend.offers.domain.Meal;
import pl.edu.pw.ee.catering_backend.orders.comms.OrderMapper;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.AddOrderDTO;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.OrderDto;
import pl.edu.pw.ee.catering_backend.orders.infrastructure.OrderStatus;
import pl.edu.pw.ee.catering_backend.user.infrastructure.UserDbMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersService implements IOrdersService {

    private final IOrdersPersistenceService ordersPersistenceService;
    private final MealRepository mealRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserDbMapper userDbMapper;
    private final OrderMapper orderMapper;
    private final MealMapper mealMapper;

    @Override
    public Order addOrder(AddOrderDTO addOrderDTO) {
        Order order = orderMapper.mapDtoToDomainModel(addOrderDTO);
        List<MealDb> dbMeals = mealRepository.findAllById(addOrderDTO.getMealIds());
        List<Meal> mappedMeals = dbMeals.stream()
                .map(mealMapper::mapToDomain)
                .toList();
        LocalDateTime orderCreationTime = LocalDateTime.now();
        UserDb userDb = userRepository.findByLogin(addOrderDTO.getClientLogin()).orElseGet(() -> {
            throw new NoSuchElementException("User with login " + addOrderDTO.getClientLogin() + " not found");
        });
        BigDecimal totalPrice = mappedMeals.stream()
                .map(Meal::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setMeals(mappedMeals);
        order.setOrderCreationTime(orderCreationTime);
        order.setClient(userDbMapper.toDomain(userDb));
        order.setStatus(OrderStatus.UNPAID);
        order.setTotalPrice(totalPrice);

        order.validateForUpdate();

        Order savedOrder = ordersPersistenceService.save(order, dbMeals);
        return savedOrder;
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream().map(this::mapAndFillIn).toList();
    }

    // TODO fix mapping
    @Override
    public OrderDto getOrderById(UUID id) {
        return orderRepository.findById(id)
                .map(this::mapAndFillIn)
                .orElseThrow(() -> new NoSuchElementException("Order with ID " + id + " not found"));
    }

    private OrderDto mapAndFillIn(OrderDb orderDb) {
        var order = orderMapper.mapDbToDomainModel(orderDb);
        var mealsIds = mealRepository.findAllById(orderDb.getMeals().stream().map(MealDb::getId).toList());
        var orderDto = orderMapper.mapToOrderDTO(order);

        return orderDto;
    }
}