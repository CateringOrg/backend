package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

}