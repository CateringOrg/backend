package pl.edu.pw.ee.catering_backend.offers.infrastructure;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository;
import pl.edu.pw.ee.catering_backend.offers.comms.MealMapper;
import pl.edu.pw.ee.catering_backend.offers.domain.IMealsPersistenceService;
import pl.edu.pw.ee.catering_backend.offers.domain.Meal;

@Service
@RequiredArgsConstructor
public class MealJpaPersistenceService implements IMealsPersistenceService {

  private final MealRepository mealRepository;
  private final MealMapper mealMapper;

  @Override
  public Meal save(Meal meal) {
    MealDb mealDb = mealMapper.mapToDb(meal);

    if (mealDb.getPhotoUrls() != null) {
      mealDb.getPhotoUrls().forEach(photoUrl -> photoUrl.setMeal(mealDb));
    }

    return mealMapper.mapToDomain(
            mealRepository.save(mealDb)
    );
  }

  @Override
  public List<Meal> getMeals() {
    return mealRepository.findAll().stream()
            .map(mealMapper::mapToDomain)
            .collect(java.util.stream.Collectors.toList());
  }

  @Override
  public Meal getById(UUID id) {
    return mealRepository.findById(id)
            .map(mealMapper::mapToDomain)
            .orElseThrow(() -> new NoSuchElementException("Meal with ID " + id + " not found"));
  }

  @Override
  public List<Meal> getMealsByCompany(UUID cateringCompanyId) {
    return mealRepository.findByCateringCompany_Id(cateringCompanyId).stream()
            .map(mealMapper::mapToDomain)
            .collect(java.util.stream.Collectors.toList());
  }
}
