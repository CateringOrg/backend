package pl.edu.pw.ee.catering_backend.offers.comms;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealWithPhotosDTO;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

public interface ICateringCompanyOffersData {
  ResponseEntity<Void> addMeal(UUID cateringCompanyId, AddMealWithPhotosDTO addMealWithPhotosDTO);//, AddMealWithPhotosDTO addMealDTO);
  List<GetMealDTO> getMealsByCompany(@PathVariable UUID cateringCompanyId);
}
