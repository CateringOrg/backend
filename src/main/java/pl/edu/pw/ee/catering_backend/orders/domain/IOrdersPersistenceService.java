package pl.edu.pw.ee.catering_backend.orders.domain;


import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;

import java.util.List;

public interface IOrdersPersistenceService {
    Order save(Order order, List<MealDb> associatedMeals);
}
