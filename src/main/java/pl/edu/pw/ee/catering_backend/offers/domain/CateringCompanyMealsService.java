package pl.edu.pw.ee.catering_backend.offers.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompanyPersistenceService;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;
import pl.edu.pw.ee.catering_backend.offers.comms.MealMapper;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class CateringCompanyMealsService implements ICateringCompanyMealsService {

  private final IMealsPersistenceService mealsPersistenceService;
  private final ICateringCompanyPersistenceService cateringCompanyPersistenceService;
  private final MealMapper mealMapper;

  @Override
  public Meal addMeal(UUID cateringCompanyId, AddMealDTO addMealDTO) {
    Meal meal = mealMapper.mapToDomain(addMealDTO);
    var cateringCompany = cateringCompanyPersistenceService.get(cateringCompanyId);

    meal.setCateringCompany(cateringCompany);
    meal.validate();

    return mealsPersistenceService.save(meal);
  }

  public List<GetMealDTO> getMealsByCompany(UUID cateringCompanyId) {
    return mealsPersistenceService.getMealsByCompany(cateringCompanyId).stream()
            .map(mealMapper::mapToGetMealDTO)
            .collect(java.util.stream.Collectors.toList());
  }
}
