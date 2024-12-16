package pl.edu.pw.ee.catering_backend.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.UserRepository;
import pl.edu.pw.ee.catering_backend.user.domain.User;
import pl.edu.pw.ee.catering_backend.user.domain.UserPersistenceService;

@Service
@RequiredArgsConstructor
class UserJpaPersistenceService implements UserPersistenceService {

  private final UserRepository userRepository;
  private final UserDbMapper userDbMapper;

  @Override
  public User getByLogin(String login) {
    return userDbMapper.toDomain(userRepository.findByLogin(login).orElseThrow());
  }

  @Override
  public User save(User user) {
    var ex = userRepository.findByLogin(user.getLogin()).orElse(null);
    UserDb db = userDbMapper.toDb(user);

    if (ex != null) {
      db.setCart(ex.getCart());
      db.setOrders(ex.getOrders());
      db.setReviews(ex.getReviews());
      db.setComplaints(ex.getComplaints());
    }
    return userDbMapper.toDomain(userRepository.save(db));
  }
}
