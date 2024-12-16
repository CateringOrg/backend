package pl.edu.pw.ee.catering_backend.offers.domain;

import jakarta.validation.Validation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.ee.catering_backend.catering_company.domain.CateringCompany;
import pl.edu.pw.ee.catering_backend.infrastructure.db.PhotoUrl;

@Getter
@Setter
public class Meal {

  private UUID id;

  @NotBlank
  private String name;

  private String description;

  @NotNull
  private BigDecimal price;

  private List<PhotoUrl> photoUrls;

  private CateringCompany cateringCompany;

  public void validate() {
    var violations = Validation.buildDefaultValidatorFactory().getValidator().validate(this);
    if (!violations.isEmpty()) {
      throw new InvalidMealDataException("Invalid meal data!", violations.stream().map(
              violation -> "%s %s".formatted(
                      violation.getPropertyPath().toString(),
                      violation.getMessage())).toList());
    }
  }
}
