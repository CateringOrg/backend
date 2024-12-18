package pl.edu.pw.ee.catering_backend.offers.application;

import java.util.Map;
import java.util.NoSuchElementException;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.edu.pw.ee.catering_backend.offers.domain.InvalidMealDataException;

@ControllerAdvice(basePackages = "pl.edu.pw.ee.catering_backend.offers.application")
public class OffersControllerAdvice {

  @ExceptionHandler(InvalidMealDataException.class)
  public ResponseEntity<?> handle(InvalidMealDataException e) {
    return ResponseEntity.badRequest()
            .body(Map.of("message", e.getMessage(), "errors", e.getViolations().toString()));
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<?> handleNoSuchElement(NoSuchElementException e) {
    return ResponseEntity.status(404)
            .body(Map.of("message", "Nie znaleziono zasobu", "details", e.getMessage()));
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
}
