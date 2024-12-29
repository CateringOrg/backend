package pl.edu.pw.ee.catering_backend.user.domain;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.user.domain.exceptions.UserAlreadyExistsException;

@Slf4j
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

  private final UserPersistenceService userPersistenceService;
  private final UserPasswordEncoder passwordEncoder;


  @Override
  public User register(String login, String password) {
    try {
      getByLogin(login);
      throw new UserAlreadyExistsException("User with login " + login + " already exists");
    } catch (NoSuchElementException ignored) {
      // user does not exist, continue
    }
    var user = User.builder().login(login).hash(passwordEncoder.encode(password))
        .role(AppRole.CLIENT).build();
        
    return userPersistenceService.save(user);
  }

  @Override
  public User getByLogin(String login) {
    return userPersistenceService.getByLogin(login);
  }
}
