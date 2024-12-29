package pl.edu.pw.ee.catering_backend.user.domain;

public interface UserPasswordEncoder {
  String encode(String password);
}
