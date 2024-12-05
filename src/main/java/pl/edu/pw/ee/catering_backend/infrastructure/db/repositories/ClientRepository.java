package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String> {

    List<Client> findByLogin(String login);
}