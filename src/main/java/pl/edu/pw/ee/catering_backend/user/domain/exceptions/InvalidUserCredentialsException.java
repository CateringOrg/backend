package pl.edu.pw.ee.catering_backend.user.domain.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {

  public InvalidUserCredentialsException(String message) {
    super(message);
  }
}
