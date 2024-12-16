package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;

public interface UserRepository extends JpaRepository<UserDb, String> {

    Optional<UserDb> findByLogin(String login);
}
