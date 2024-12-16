package pl.edu.pw.ee.catering_backend.authentication.comms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

  private String login;
  private String password;
}
