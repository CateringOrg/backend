package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Cart;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByClient_Login(String clientLogin);
}