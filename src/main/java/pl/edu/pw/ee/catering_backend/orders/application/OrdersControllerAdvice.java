package pl.edu.pw.ee.catering_backend.orders.application;

import java.util.Map;
import java.util.NoSuchElementException;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.edu.pw.ee.catering_backend.orders.domain.InvalidOrderDataException;

@ControllerAdvice(basePackages = "pl.edu.pw.ee.catering_backend.orders.application")
public class OrdersControllerAdvice {

    @ExceptionHandler(InvalidOrderDataException.class)
    public ResponseEntity<?> handle(InvalidOrderDataException e) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", e.getMessage(), "errors", e.getViolations().toString()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElement(NoSuchElementException e) {
        return ResponseEntity.status(404)
                .body(Map.of("message", "Nie znaleziono zamówienia", "details", e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e) {
        var violations = e.getConstraintViolations().stream()
                .map(violation -> Map.of(
                        "field", violation.getPropertyPath().toString(),
                        "error", violation.getMessage()))
                .toList();


        return ResponseEntity.badRequest()
                .body(Map.of("message", "Invalid data", "errors", violations));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handle(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
}