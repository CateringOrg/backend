package pl.edu.pw.ee.catering_backend.authentication.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.authentication.comms.dtos.JwtTokenDto;
import pl.edu.pw.ee.catering_backend.user.domain.User;

@Service
@RequiredArgsConstructor
public class AppAuthService {


  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public JwtTokenDto login(String login, String password) {
    var a = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(login, password));

    var accessToken = jwtService.generateJwtTokenForUser((User) a.getPrincipal());

    return new JwtTokenDto(accessToken.getToken());
  }

}
