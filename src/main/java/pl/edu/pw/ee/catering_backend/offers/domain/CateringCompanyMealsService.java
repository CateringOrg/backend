package pl.edu.pw.ee.catering_backend.offers.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompanyPersistenceService;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;
import pl.edu.pw.ee.catering_backend.offers.comms.MealMapper;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

import java.util.List;
import java.util.NoSuchElementException;
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
    var cateringCompany = cateringCompanyPersistenceService.getById(cateringCompanyId);

    meal.setCateringCompany(cateringCompany);
    meal.validate();

    return mealsPersistenceService.save(meal);
  }

  @Override
  public boolean updateMeal(UUID cateringCompanyId, UUID mealId, AddMealDTO addMealDTO) {
    var cateringCompany = cateringCompanyPersistenceService.getById(cateringCompanyId);
    if (cateringCompany == null) {
      return false;
    }

    var existingMeal = mealsPersistenceService.getById(mealId);
    if (existingMeal == null) {
      return false;
    }

    if (!existingMeal.getCateringCompany().getId().equals(cateringCompanyId)) {
      return false;
    }

    var updateMeal = mealMapper.mapToDomain(addMealDTO);

    existingMeal.setName(updateMeal.getName());
    existingMeal.setDescription(updateMeal.getDescription());
    existingMeal.setPrice(updateMeal.getPrice());
    existingMeal.setPhotoUrls(updateMeal.getPhotoUrls());

    existingMeal.validate();

    mealsPersistenceService.save(existingMeal);

    return true;
  }

  @Override
  public List<GetMealDTO> getMealsByCompany(UUID cateringCompanyId) {
    return mealsPersistenceService.getMealsByCompany(cateringCompanyId).stream()
            .map(mealMapper::mapToGetMealDTO)
            .collect(java.util.stream.Collectors.toList());
  }

  @Override
  public GetMealDTO getCompanyMeal(UUID cateringCompanyId, UUID mealId) {
    var cateringCompany = cateringCompanyPersistenceService.getById(cateringCompanyId);
    var meal = mealsPersistenceService.getById(mealId);

    if (!meal.getCateringCompany().getId().equals(cateringCompanyId)) {
      throw new NoSuchElementException("Meal from company not found");
    }

    return mealMapper.mapToGetMealDTO(mealsPersistenceService.getById(mealId));
  }
}
