package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.CartDb;

public interface CartRepository extends JpaRepository<CartDb, UUID> {
    Optional<CartDb> findByClient_Login(String clientLogin);
}
