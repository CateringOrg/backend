package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.ClientDb;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientDb, String> {
    Optional<ClientDb> findByLogin(String login);
}
