package pl.edu.pw.ee.catering_backend.user.domain;

public interface UserPersistenceService {

  User getByLogin(String login);

  User save(User user);
}
