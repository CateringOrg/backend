package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Entity(name = "clients")
@Data
@ToString(exclude = {"cart", "reviews", "complaints", "orders"})
public class ClientDb {

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
}
