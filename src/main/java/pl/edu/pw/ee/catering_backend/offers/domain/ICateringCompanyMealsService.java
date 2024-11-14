package pl.edu.pw.ee.catering_backend.offers.domain;

import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;

import java.util.UUID;

public interface ICateringCompanyMealsService {
  Meal addMeal(UUID cateringCompanyId, AddMealDTO addMealDTO);
}
