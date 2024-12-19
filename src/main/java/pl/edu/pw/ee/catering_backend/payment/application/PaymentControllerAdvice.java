package pl.edu.pw.ee.catering_backend.payment.application;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice(basePackages = "pl.edu.pw.ee.catering_backend.payment.application")
public class PaymentControllerAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElement(NoSuchElementException e) {
        return ResponseEntity.status(404)
                .body(Map.of("message", "Not found", "details", e.getMessage()));
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
