package pl.edu.pw.ee.catering_backend.authentication.application;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.catering_backend.authentication.application.services.AppAuthService;
import pl.edu.pw.ee.catering_backend.authentication.comms.IAuthorizationData;
import pl.edu.pw.ee.catering_backend.authentication.comms.dtos.JwtTokenDto;
import pl.edu.pw.ee.catering_backend.authentication.comms.dtos.LoginRequestDto;
import pl.edu.pw.ee.catering_backend.authentication.comms.dtos.RegisterRequestDto;
import pl.edu.pw.ee.catering_backend.user.domain.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController implements IAuthorizationData {

  private final UserService userService;
  private final AppAuthService appAuthService;

  @Operation(summary = "Login")
  @PostMapping("/login")
  @Override
  public JwtTokenDto login(LoginRequestDto loginRequestDto) {
    return appAuthService.login(loginRequestDto.getLogin(), loginRequestDto.getPassword());
  }

  @Operation(summary = "Register")
  @PostMapping("/register")
  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void register(RegisterRequestDto registerRequestDto) {
    userService.register(registerRequestDto.getLogin(), registerRequestDto.getPassword());
  }

}
