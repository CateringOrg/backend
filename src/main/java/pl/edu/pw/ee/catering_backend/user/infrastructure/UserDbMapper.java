package pl.edu.pw.ee.catering_backend.user.infrastructure;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;
import pl.edu.pw.ee.catering_backend.user.domain.User;

@Mapper(componentModel = "spring")
interface UserDbMapper {


  User toDomain(UserDb userDb);

  @Mapping(target = "reviews", ignore = true)
  @Mapping(target = "orders", ignore = true)
  @Mapping(target = "complaints", ignore = true)
  UserDb toDb(User user);
}
