package pl.edu.pw.ee.catering_backend.cart.domain;

import jakarta.validation.Validation;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.ee.catering_backend.offers.domain.Meal;

@Getter
@Setter
public class Cart {

    private UUID id;

    @NotNull
    private String clientLogin;

    @NotNull
    private List<Meal> meals = new ArrayList<>();

    public void validate() {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(this);

        if (!violations.isEmpty()) {
            throw new InvalidCartDataException("Invalid cart data!", violations.stream().map(
                    violation -> "%s %s".formatted(
                            violation.getPropertyPath().toString(),
                            violation.getMessage())).toList());
        }

        if (meals != null) {
            for (Meal meal : meals) {
                meal.validate();
            }
        }
    }
}
