package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Meal;

public interface MealRepository extends JpaRepository<Meal, UUID> {

    List<Meal> findByCateringCompany_Id(UUID cateringCompanyId);
}