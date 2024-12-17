package pl.edu.pw.ee.catering_backend.orders.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.OrderDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.OrderRepository;
import pl.edu.pw.ee.catering_backend.orders.comms.OrderMapper;
import pl.edu.pw.ee.catering_backend.orders.domain.IOrdersPersistenceService;
import pl.edu.pw.ee.catering_backend.orders.domain.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderJpaPersistenceService implements IOrdersPersistenceService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order save(Order order, List<MealDb> associatedMeals) {
        OrderDb orderEntity = orderMapper.mapToOrderDb(order);

        orderEntity.setMeals(associatedMeals);

        for (MealDb meal : associatedMeals) {
            if (meal.getOrders() == null) {
                meal.setOrders(new ArrayList<>());
            }
            if (!meal.getOrders().contains(orderEntity)) {
                meal.getOrders().add(orderEntity);
            }
        }

        orderEntity.setComplaint(Collections.emptyList());

        // Save the entity
        OrderDb savedOrderEntity = orderRepository.save(orderEntity);

        return orderMapper.mapDbToDomainModel(savedOrderEntity);
    }
}
