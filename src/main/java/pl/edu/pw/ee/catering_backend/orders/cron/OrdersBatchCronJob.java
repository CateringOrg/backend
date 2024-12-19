package pl.edu.pw.ee.catering_backend.orders.cron;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.edu.pw.ee.catering_backend.infrastructure.cron.BaseCronJob;
import pl.edu.pw.ee.catering_backend.infrastructure.db.OrderDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.OrderRepository;
import pl.edu.pw.ee.catering_backend.orders.comms.OrderMapper;
import pl.edu.pw.ee.catering_backend.orders.domain.Order;
import pl.edu.pw.ee.catering_backend.orders.infrastructure.OrderStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class OrdersBatchCronJob extends BaseCronJob {

    private final String cronExpression;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final IOrderDispatcher orderDispatcher;
    private final Logger logger = LoggerFactory.getLogger(OrdersBatchCronJob.class);

    public OrdersBatchCronJob(
            @Value("${cron.expression}") String cronExpression,
            OrderRepository orderRepository,
            OrderMapper orderMapper, IOrderDispatcher orderDispatcher) {
        this.cronExpression = cronExpression;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderDispatcher = orderDispatcher;
    }

    @Override
    public void execute() {
        System.out.println("Executing meals cron job: Aggregating and sending meals...");

        List<Order> orders = collectOrdersWithDeadline(2);

        if (orders.isEmpty()) {
            logger.info("No orders to dispatch");
            return;
        }

        Map<Boolean, Order> batchResults = orderDispatcher.sendBatch(orders);

        List<Order> failedPayloads = batchResults.entrySet().stream()
                .filter(entry -> !entry.getKey())
                .map(Map.Entry::getValue)
                .toList();

        failedPayloads.forEach(payload -> logger.error("Failed to send order dispatch payload: {}", payload));

        List<Order> successfulPayloads = batchResults.entrySet().stream()
                .filter(Map.Entry::getKey)
                .peek(entry -> logger.info("Successfull payload from batchResults: {}", entry.getValue()))
                .map(Map.Entry::getValue)
                .toList();

        successfulPayloads.forEach((order) -> {
            orderRepository.updateOrderStatus(order.getOrderId(), OrderStatus.IN_PREPARATION);
            logger.info("Order {} status changed to IN_PREPARATION", order.getOrderId());
        });

    }

    /**
     * Zwraca częstotliwość CRON-a z konfiguracji.
     */
    @Override
    public String getFrequency() {
        return cronExpression;
    }

    private List<Order> collectOrdersWithDeadline(int daysUntilDeadline) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime deadlineThreshold = today.plusDays(daysUntilDeadline);
        List<Order> orders = prepareMeals();

        logger.info("Orders to prepare: {}", orders);
        return orders.stream()
                .filter(order -> {
                    // TODO change this status check to PAID after implementing payment
                    boolean shouldBeSent = daysBetween(today, order.getDeliveryTime()) <= daysUntilDeadline && order.getStatus() == OrderStatus.PAID;

                    logger.info("Order {} is within deadline: {}", order.getOrderId(), shouldBeSent);
                    logger.info("Order day difference: {}", daysBetween(today, order.getDeliveryTime()));
                    logger.info("Order delivery time: {} deadline threshold: {}", order.getDeliveryTime(), deadlineThreshold);

                    return shouldBeSent;
                })
                .toList();
    }

    @Transactional
    public List<Order> prepareMeals() {
        List<OrderDb> orders = orderRepository.findAllWithMeals();
        return orders.stream()
                .map(orderMapper::mapDbToDomainModel)
                .toList();
    }

    private long daysBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.DAYS.between(start, end);
    }
}
