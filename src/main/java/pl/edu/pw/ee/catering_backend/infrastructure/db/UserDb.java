package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import pl.edu.pw.ee.catering_backend.user.domain.AppRole;

@Entity(name = "users")
@Data
@ToString(exclude = {"cart", "reviews", "complaints", "orders"})
public class UserDb {

  @Id
  @Column(unique = true)
  private String login;

  private String hash;

  @Embedded
  private Wallet wallet;

  @OneToOne(optional = true, mappedBy = "client")
  private CartDb cart;

  @OneToMany(mappedBy = "client")
  private List<ReviewDb> reviews;

  @OneToMany(mappedBy = "client")
  private List<ComplaintDb> complaints;

  @OneToMany(mappedBy = "client")
  private List<OrderDb> orders;

  @Enumerated(EnumType.STRING)
  private AppRole role;
}
