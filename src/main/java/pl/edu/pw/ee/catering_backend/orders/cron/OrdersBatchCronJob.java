package pl.edu.pw.ee.catering_backend.orders.cron;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.edu.pw.ee.catering_backend.catering_company.domain.CateringCompany;
import pl.edu.pw.ee.catering_backend.infrastructure.cron.BaseCronJob;
import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.CateringCompanyRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.OrderRepository;
import pl.edu.pw.ee.catering_backend.offers.comms.MealMapper;
import pl.edu.pw.ee.catering_backend.offers.domain.Meal;
import pl.edu.pw.ee.catering_backend.orders.comms.OrderMapper;
import pl.edu.pw.ee.catering_backend.orders.domain.Order;
import pl.edu.pw.ee.catering_backend.orders.infrastructure.OrderStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Transactional
public class OrdersBatchCronJob extends BaseCronJob {

    private final String cronExpression;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final IOrderDispatcher orderDispatcher;
    private final Logger logger = LoggerFactory.getLogger(OrdersBatchCronJob.class);
    private final MealMapper mealMapper;

    public OrdersBatchCronJob(
            @Value("${cron.expression}") String cronExpression,
            OrderRepository orderRepository,
            OrderMapper orderMapper, IOrderDispatcher orderDispatcher,
            MealMapper mealMapper) {
        this.cronExpression = cronExpression;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderDispatcher = orderDispatcher;
        this.mealMapper = mealMapper;
    }

    @Override
    public void execute() {
        System.out.println("Executing meals cron job: Aggregating and sending meals...");

        List<OrderDispatchPayload> orders = collectOrdersWithDeadline(2);

        if (orders.isEmpty()) {
            logger.info("No orders to dispatch");
            return;
        }

        Map<Boolean, OrderDispatchPayload> batchResults = orderDispatcher.sendBatch(orders);

        List<OrderDispatchPayload> failedPayloads = batchResults.entrySet().stream()
                .filter(entry -> !entry.getKey())
                .map(Map.Entry::getValue)
                .toList();

        failedPayloads.forEach(payload -> {
            logger.error("Failed to send order dispatch payload: {}", payload);
        });

        List<OrderDispatchPayload> successfulPayloads = batchResults.entrySet().stream()
                .filter(Map.Entry::getKey)
                .map(Map.Entry::getValue)
                .toList();

        Map<Order, List<Meal>> groupedOrders = successfulPayloads.stream()
                .flatMap(payload -> payload.meals().stream()
                        .flatMap(meal -> findOrdersByMeal(meal).stream()
                                .map(order -> Map.entry(order, meal))
                        )
                )
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        groupedOrders.forEach((order, meals) -> {
            order.setStatus(OrderStatus.IN_PREPARATION);
            orderRepository.save(orderMapper.mapToOrderDb(order));
            logger.debug("Order {} status changed to IN_PREPARATION", order.getOrderId());
        });

    }

    /**
     * Zwraca częstotliwość CRON-a z konfiguracji.
     */
    @Override
    public String getFrequency() {
        return cronExpression;
    }

    private List<OrderDispatchPayload> collectOrdersWithDeadline(int daysUntilDeadline) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime deadlineThreshold = today.plusDays(daysUntilDeadline);

        List<Meal> mealsToSend = orderRepository.findAll().stream()
                .filter(order ->
                        order.getStatus() == OrderStatus.PAID
                                && daysBetween(today, order.getDeliveryTime()) <= daysUntilDeadline
                                && order.getDeliveryTime().isBefore(deadlineThreshold)
                )
                .map(orderMapper::mapDbToDomainModel)
                .map(Order::getMeals)
                .flatMap(List::stream)
                .toList();

        return mealsToSend
                .stream()
                .collect(Collectors.groupingBy(Meal::getCateringCompany))
                .entrySet()
                .stream()
                .map(entry -> {
                    CateringCompany cateringCompany = entry.getKey();
                    List<Meal> meals = entry.getValue();
                    return new OrderDispatchPayload(cateringCompany, meals);
                })
                .toList();
    }

    private List<Order> findOrdersByMeal(Meal meal) {
        MealDb mealDb = mealMapper.mapToDb(meal);
        return orderRepository.findAll().stream()
                .filter(order -> order.getMeals().contains(mealDb))
                .map(orderMapper::mapDbToDomainModel)
                .toList();
    }

    private long daysBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.DAYS.between(start, end);
    }
}
