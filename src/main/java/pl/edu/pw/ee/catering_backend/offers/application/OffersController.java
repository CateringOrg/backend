package pl.edu.pw.ee.catering_backend.offers.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.catering_backend.offers.comms.ICateringCompanyOffersData;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;
import pl.edu.pw.ee.catering_backend.offers.domain.ICateringCompanyMealsService;
import pl.edu.pw.ee.catering_backend.offers.domain.IClientOffersService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OffersController implements IClientOffersService, ICateringCompanyOffersData {

  private final ICateringCompanyMealsService cateringCompanyMealsService;
  private final IClientOffersService clientOffersService;

  @Override
  @PostMapping("/{cateringCompanyId}/meals")
  @Operation(summary = "Add a new meal to the catering company's offer")
  @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Meal added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid meal data; violations in data provided"),
        @ApiResponse(responseCode = "404", description = "Catering company not found")
  })
  public ResponseEntity<Void> addMeal(@PathVariable UUID cateringCompanyId, @RequestBody AddMealDTO addMealDTO) {
    cateringCompanyMealsService.addMeal(cateringCompanyId, addMealDTO);
    return ResponseEntity.noContent().build();
  }

  @Override
  @PostMapping("/search/meals")
  @Operation(summary = "Get current meals offer")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Meals requested successfully"),
          @ApiResponse(responseCode = "404", description = "Could not find any meals")
  })
  public List<GetMealDTO> getMeals() {
    List<GetMealDTO> meals = clientOffersService.getMeals();
    return ResponseEntity.ok(meals).getBody();
  }
}