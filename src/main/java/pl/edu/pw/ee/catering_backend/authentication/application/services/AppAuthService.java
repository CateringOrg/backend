package pl.edu.pw.ee.catering_backend.authentication.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.authentication.comms.dtos.JwtTokenDto;
import pl.edu.pw.ee.catering_backend.user.domain.UserService;
import pl.edu.pw.ee.catering_backend.user.domain.exceptions.InvalidUserCredentialsException;

@Service
@RequiredArgsConstructor
public class AppAuthService {

  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public JwtTokenDto login(String login, String password) {

    var user = userService.getByLogin(login);

    if (!passwordEncoder.matches(password, user.getHash())) {
      throw new InvalidUserCredentialsException("Invalid credentials");
    }

    var accessToken = jwtService.generateJwtTokenForUser(user);

    return new JwtTokenDto(accessToken.getToken());
  }

}
