package pl.edu.pw.ee.catering_backend.orders.domain;

import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public Order addOrder(
            String userLogin,
            AddOrderDTO addOrderDTO
    ) {
        Order order = orderMapper.mapDtoToDomainModel(addOrderDTO);
        List<MealDb> dbMeals = mealRepository.findAllById(addOrderDTO.getMealIds());
        List<Meal> mappedMeals = dbMeals.stream()
                .map(mealMapper::mapToDomain)
                .toList();
        LocalDateTime orderCreationTime = LocalDateTime.now();
        UserDb userDb = userRepository.findByLogin(userLogin).orElseThrow(() -> new NoSuchElementException("User with login " + userLogin + " not found"));
        BigDecimal totalPrice = mappedMeals.stream()
                .map(Meal::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (mappedMeals.size() != addOrderDTO.getMealIds().size()) {
            List<UUID> notFoundMealIds = addOrderDTO.getMealIds().stream()
                    .filter(mealId -> dbMeals.stream().noneMatch(mealDb -> mealDb.getId().equals(mealId)))
                    .toList();
            throw new IllegalArgumentException("Some meals were not found in the database: " + notFoundMealIds);
        }

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
    public List<OrderDto> getOrders(String userLogin) {
        UserDb userDb = userRepository.findByLogin(userLogin).orElseThrow(() -> new NoSuchElementException("User with login " + userLogin + " not found"));
        List<OrderDb> orders = orderRepository.findAll().stream()
                .filter(orderDb -> orderDb.getClient().equals(userDb))
                .toList();

        List<OrderDto> orderDtos = orders.stream()
                .map(orderMapper::mapDbToDomainModel)
                .map(orderMapper::mapToOrderDto)
                .toList();

        return orderDtos;
    }

    // TODO fix mapping
    @Override
    public OrderDto getOrderById(UUID id) {
        OrderDb orderDb = orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Order with id " + id + " not found"));
        Order order = orderMapper.mapDbToDomainModel(orderDb);
        return orderMapper.mapToOrderDto(order);

    }

}