package pl.edu.pw.ee.catering_backend.cart.application;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.edu.pw.ee.catering_backend.cart.domain.InvalidCartDataException;

import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice(basePackages = "pl.edu.pw.ee.catering_backend.cart.application")
public class CartControllerAdvice {

    @ExceptionHandler(InvalidCartDataException.class)
    public ResponseEntity<?> handleInvalidCartData(InvalidCartDataException e) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "message", e.getMessage(),
                        "errors", e.getViolations()
                ));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElement(NoSuchElementException e) {
        return ResponseEntity.status(404)
                .body(Map.of(
                        "message", "Nie znaleziono zasobu",
                        "details", e.getMessage()
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e) {
        var violations = e.getConstraintViolations().stream()
                .map(violation -> Map.of(
                        "field", violation.getPropertyPath().toString(),
                        "error", violation.getMessage()
                ))
                .toList();

        return ResponseEntity.badRequest()
                .body(Map.of(
                        "message", "Nieprawidłowe dane",
                        "errors", violations
                ));
    }
}
