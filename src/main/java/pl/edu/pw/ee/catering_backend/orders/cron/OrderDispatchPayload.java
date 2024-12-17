package pl.edu.pw.ee.catering_backend.orders.cron;

import pl.edu.pw.ee.catering_backend.catering_company.domain.CateringCompany;
import pl.edu.pw.ee.catering_backend.offers.domain.Meal;

import java.util.List;

public record OrderDispatchPayload(CateringCompany cateringCompany, List<Meal> meals) {
}
