package pl.edu.pw.ee.catering_backend.offers.comms;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;

public interface ICateringCompanyOffersData {
  ResponseEntity<Void> addMeal(UUID cateringCompanyId, AddMealDTO addMealDTO);
}
