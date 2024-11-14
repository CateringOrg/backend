package pl.edu.pw.ee.catering_backend.offers.domain;

import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;

public interface IMealsPersistenceService {
  Meal save(Meal meal);
}
