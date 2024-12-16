package pl.edu.pw.ee.catering_backend.authentication.application;

import java.util.Map;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.edu.pw.ee.catering_backend.user.domain.exceptions.InvalidUserCredentialsException;
import pl.edu.pw.ee.catering_backend.user.domain.exceptions.UserAlreadyExistsException;

@Slf4j
@ControllerAdvice(basePackages = "pl.edu.pw.ee.catering_backend.authentication.application")
public class AuthorizationControllerAdvice {

  @ExceptionHandler({InvalidUserCredentialsException.class, NoSuchElementException.class})
  public ResponseEntity<?> handle(Exception e) {
    log.warn("Exception in auth controller", e);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("message", "Invalid credentials"));
  }

  @ExceptionHandler({UserAlreadyExistsException.class})
  public ResponseEntity<?> handle(UserAlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("message", e.getMessage()));
  }
}
