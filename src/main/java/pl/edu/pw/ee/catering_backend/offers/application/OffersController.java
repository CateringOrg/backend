package pl.edu.pw.ee.catering_backend.offers.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.catering_backend.offers.comms.ICateringCompanyOffersData;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealWithPhotosDTO;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;
import pl.edu.pw.ee.catering_backend.offers.domain.ICateringCompanyMealsService;
import pl.edu.pw.ee.catering_backend.offers.domain.IClientOffersService;
import pl.edu.pw.ee.catering_backend.offers.domain.IPhotoUploadService;

import java.util.ArrayList;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OffersController implements IClientOffersService, ICateringCompanyOffersData {

  private final ICateringCompanyMealsService cateringCompanyMealsService;
  private final IClientOffersService clientOffersService;
  private final IPhotoUploadService photoUploadService;

  @Override
  @PostMapping("/{cateringCompanyId}/meals")
  @Operation(summary = "Add a new meal to the catering company's offer")
  @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Meal added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid meal data; violations in data provided"),
        @ApiResponse(responseCode = "404", description = "Catering company not found")
  })
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CATERING')")
  public ResponseEntity<Void> addMeal(
          @PathVariable UUID cateringCompanyId,
          @ModelAttribute AddMealWithPhotosDTO addMealWithPhotosDTO
  ) {
    List<String> photoUrls;
    if(addMealWithPhotosDTO.getPhotos() != null)
      photoUrls = photoUploadService.uploadPhotos(addMealWithPhotosDTO.getPhotos());
    else
      photoUrls = new ArrayList<>();

    AddMealDTO addMealDTO = new AddMealDTO();
    addMealDTO.setName(addMealWithPhotosDTO.getName());
    addMealDTO.setDescription(addMealWithPhotosDTO.getDescription());
    addMealDTO.setPrice(addMealWithPhotosDTO.getPrice());
    addMealDTO.setAvailable(addMealWithPhotosDTO.getAvailable());
    addMealDTO.setPhotoUrls(photoUrls);

    cateringCompanyMealsService.addMeal(cateringCompanyId, addMealDTO);
    return ResponseEntity.noContent().build();
  }

  @Override
  @GetMapping("/search/meals")
  @Operation(summary = "Get current meals offer")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Meals requested successfully"),
          @ApiResponse(responseCode = "404", description = "Could not find any meals")
  })
  public List<GetMealDTO> getMeals() {
    List<GetMealDTO> meals = clientOffersService.getMeals();
    return ResponseEntity.ok(meals).getBody();
  }

    @Override
    @GetMapping("/{cateringCompanyId}/meals")
    @Operation(summary = "Get meals offer of a catering company")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meals requested successfully"),
            @ApiResponse(responseCode = "404", description = "Could not find any meals")
    })
    public List<GetMealDTO> getMealsByCompany(@PathVariable UUID cateringCompanyId) {
      List<GetMealDTO> meals = cateringCompanyMealsService.getMealsByCompany(cateringCompanyId);
      return ResponseEntity.ok(meals).getBody();
    }
}