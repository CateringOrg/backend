package pl.edu.pw.ee.catering_backend.offers.domain;

import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

import java.util.List;

public interface IMealsPersistenceService {

  pl.edu.pw.ee.catering_backend.offers.domain.Meal save(Meal meal);

  List<pl.edu.pw.ee.catering_backend.offers.domain.Meal> getMeals();
}
