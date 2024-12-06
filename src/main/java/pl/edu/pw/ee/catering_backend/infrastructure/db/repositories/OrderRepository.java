package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Client;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Meal;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM orders o WHERE o.client.login = :clientLogin")
    List<Order> findByClientLogin(String clientLogin);

    @Modifying
    @Transactional
    @Query("insert into orders (deliveryAddress, deliveryMethod, meals, status, client) values (:deliveryAddress, :deliveryMethod, :meals, :status, :client)")
    Void insertOrder(String deliveryAddress, String deliveryMethod, List<Meal> meals, String status, Client client);
}