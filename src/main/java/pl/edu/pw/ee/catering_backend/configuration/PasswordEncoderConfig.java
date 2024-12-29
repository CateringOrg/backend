package pl.edu.pw.ee.catering_backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.pw.ee.catering_backend.user.domain.UserPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserPasswordEncoder userPasswordEncoder(PasswordEncoder passwordEncoder) {
    return passwordEncoder::encode;
  }
}
