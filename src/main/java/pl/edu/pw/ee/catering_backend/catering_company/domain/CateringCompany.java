package pl.edu.pw.ee.catering_backend.catering_company.domain;

import jakarta.validation.Validation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.NIP;

@AllArgsConstructor
@Getter
@Setter
public class CateringCompany {

  private UUID id;
  @NotBlank
  private String address;
  @NotBlank
  private String name;
  @NIP
  @NotNull
  private String NIP;

  public void validate() {
    var violations = Validation.buildDefaultValidatorFactory().getValidator().validate(this);
    if (!violations.isEmpty()) {
      throw new InvalidCompanyDataException("Invalid company data!", violations.stream().map(
          cateringCompanyConstraintViolation -> "%s %s".formatted(
              cateringCompanyConstraintViolation.getPropertyPath().toString(),
              cateringCompanyConstraintViolation.getMessage())).toList());
    }
  }

  //commented out as Meals arent part of this sprint
//  private List<Meal> meals;
}
