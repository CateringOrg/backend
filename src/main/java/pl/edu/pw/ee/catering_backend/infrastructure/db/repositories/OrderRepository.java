package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.OrderDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;
import pl.edu.pw.ee.catering_backend.orders.infrastructure.OrderStatus;

public interface OrderRepository extends JpaRepository<OrderDb, UUID> {
    @Query("SELECT o FROM orders o WHERE o.client.login = :clientLogin")
    List<OrderDb> findByClientLogin(String clientLogin);

    @Modifying
    @Transactional
    @Query("insert into orders (deliveryAddress, deliveryMethod, meals, status, client) values (:deliveryAddress, :deliveryMethod, :meals, :status, :client)")
    Void insertOrder(String deliveryAddress, String deliveryMethod, List<MealDb> meals, String status, UserDb client);

    @Query("SELECT o FROM orders o LEFT JOIN FETCH o.meals")
    List<OrderDb> findAllWithMeals();

    @Modifying
    @Transactional
    @Query("update orders o set o.status = :status where o.id = :id")
    void updateOrderStatus(@Param("id") UUID id, @Param("status") OrderStatus status);
}
