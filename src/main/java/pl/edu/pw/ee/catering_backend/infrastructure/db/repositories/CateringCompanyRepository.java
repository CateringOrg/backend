package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.CateringCompany;

public interface CateringCompanyRepository extends JpaRepository<CateringCompany, UUID> {

}