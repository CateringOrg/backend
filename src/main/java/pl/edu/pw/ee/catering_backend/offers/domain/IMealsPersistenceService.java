package pl.edu.pw.ee.catering_backend.offers.domain;

import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

import java.util.List;
import java.util.UUID;

public interface IMealsPersistenceService {

  pl.edu.pw.ee.catering_backend.offers.domain.Meal save(Meal meal);

  List<pl.edu.pw.ee.catering_backend.offers.domain.Meal> getMeals();

  pl.edu.pw.ee.catering_backend.offers.domain.Meal getById(UUID id);
}
