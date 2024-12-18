package pl.edu.pw.ee.catering_backend.offers.domain;

import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

import java.util.List;
import java.util.UUID;

public interface ICateringCompanyMealsService {
  Meal addMeal(UUID cateringCompanyId, AddMealDTO addMealDTO);

  boolean updateMeal(UUID cateringCompanyId, UUID mealId, AddMealDTO addMealDTO);

  List<GetMealDTO> getMealsByCompany(UUID cateringCompanyId);
}
