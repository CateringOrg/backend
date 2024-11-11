package pl.edu.pw.ee.catering_backend.catering_company.application;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.edu.pw.ee.catering_backend.catering_company.domain.InvalidCompanyDataException;

@ControllerAdvice(basePackages = "pl.edu.pw.ee.catering_backend.catering_company.application")
public class CateringCompaniesControllerAdvice {

  @ExceptionHandler({InvalidCompanyDataException.class})
  public ResponseEntity<?> handle(InvalidCompanyDataException e) {
    return ResponseEntity.badRequest()
        .body(Map.of("message", e.getMessage(), "errors", e.getViolations().toString()));
  }
}
