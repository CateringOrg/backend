package pl.edu.pw.ee.catering_backend.catering_company.domain;

import java.util.List;
import lombok.Getter;


public class InvalidCompanyDataException extends RuntimeException {

  @Getter
  private final List<String> violations;

  public InvalidCompanyDataException(String message, List<String> violations) {
    super(message);
    this.violations = violations;
  }
}
