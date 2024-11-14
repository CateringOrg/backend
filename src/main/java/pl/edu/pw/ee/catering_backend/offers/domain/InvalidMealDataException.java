package pl.edu.pw.ee.catering_backend.offers.domain;

import java.util.List;

import lombok.Getter;

public class InvalidMealDataException extends RuntimeException {

  @Getter
  private final List<String> violations;

  public InvalidMealDataException(String message, List<String> violations) {
    super(message);
    this.violations = violations;
  }
}
