package pl.edu.pw.ee.catering_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.modulith.Modulithic;

@Modulithic
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class CateringBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(CateringBackendApplication.class, args);
  }

}
