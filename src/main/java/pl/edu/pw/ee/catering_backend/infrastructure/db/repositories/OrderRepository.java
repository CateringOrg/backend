package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM orders o WHERE o.client.login = :clientLogin")
    List<Order> findByClientLogin(String clientLogin);
}