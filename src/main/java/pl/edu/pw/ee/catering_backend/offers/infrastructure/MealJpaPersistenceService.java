package pl.edu.pw.ee.catering_backend.offers.infrastructure;

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
}
