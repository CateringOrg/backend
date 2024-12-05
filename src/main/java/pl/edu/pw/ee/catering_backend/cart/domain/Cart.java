package pl.edu.pw.ee.catering_backend.cart.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.ee.catering_backend.offers.domain.Meal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Cart {

    private UUID id;

    @Nullable
    private String clientLogin;

    @NotNull
    private List<Meal> meals=new ArrayList<>();;

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
