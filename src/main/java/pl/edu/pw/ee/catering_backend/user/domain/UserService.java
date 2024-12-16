package pl.edu.pw.ee.catering_backend.user.domain;

public interface UserService {

  User register(String login, String password);

  User getByLogin(String login);

}
