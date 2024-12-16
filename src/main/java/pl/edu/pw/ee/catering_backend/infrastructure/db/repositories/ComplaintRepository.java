package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.ComplaintDb;

public interface ComplaintRepository extends JpaRepository<ComplaintDb, Integer> {

}
