package pl.edu.pw.ee.catering_backend.authentication.comms;

import pl.edu.pw.ee.catering_backend.authentication.comms.dtos.JwtTokenDto;
import pl.edu.pw.ee.catering_backend.authentication.comms.dtos.LoginRequestDto;
import pl.edu.pw.ee.catering_backend.authentication.comms.dtos.RegisterRequestDto;

public interface IAuthorizationData {

  JwtTokenDto login(LoginRequestDto loginRequestDto);

  void register(RegisterRequestDto registerRequestDto);

}
