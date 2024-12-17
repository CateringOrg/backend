package pl.edu.pw.ee.catering_backend.orders.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class InvalidOrderDataException extends RuntimeException {

    @Getter
    private final List<String> violations;

    public InvalidOrderDataException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " " + Arrays.toString(violations.toArray());
    }
}