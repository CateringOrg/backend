package pl.edu.pw.ee.catering_backend.orders.domain;

import jakarta.validation.Validation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.ee.catering_backend.infrastructure.db.ComplaintDb;
import pl.edu.pw.ee.catering_backend.offers.domain.Meal;
import pl.edu.pw.ee.catering_backend.orders.infrastructure.OrderStatus;
import pl.edu.pw.ee.catering_backend.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

interface OrderValidation extends Default {}
interface UpdateValidation extends OrderValidation {}


@AllArgsConstructor
@Getter
@Setter
public class Order {
    private UUID orderId;

    @NotBlank
    private String deliveryAddress;
    @NotBlank
    private String deliveryMethod;
    @NotNull
    private LocalDateTime deliveryTime;
    @NotNull(groups = UpdateValidation.class)
    private LocalDateTime orderCreationTime;
    @NotNull(groups = UpdateValidation.class)
    private OrderStatus status;
    @NotEmpty(groups = UpdateValidation.class)
    private List<Meal> meals;
    @NotNull(groups = UpdateValidation.class)
    private User client;
    @NotNull(groups = UpdateValidation.class)
    private BigDecimal totalPrice;
    // TODO change to complaints from domain
    private List<ComplaintDb> complaint;

    private void validate(Class<?> group) {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(this, group);
        if (!violations.isEmpty()) {
            throw new InvalidOrderDataException("Invalid Order data!", violations.stream()
                    .map(constraintViolation -> "%s %s".formatted(
                            constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getMessage()))
                    .collect(Collectors.toList()));
        }
    }

    public void validateForUpdate() {
        validate(UpdateValidation.class);
    }

}
