package pl.edu.pw.ee.catering_backend.offers.domain;

import java.util.List;
import java.util.UUID;

public interface IMealsPersistenceService {

  Meal save(Meal meal);

  List<Meal> getMeals();

  Meal getById(UUID id);

  List<Meal> getMealsByCompany(UUID cateringCompanyId);
}
