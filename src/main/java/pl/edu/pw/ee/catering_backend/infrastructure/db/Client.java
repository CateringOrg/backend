package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "clients_table")
@Data
public class Client {

  @Id
  @Column(unique = true)
  private String login;

  private String hash;

  @Embedded
  private Wallet wallet;

  @OneToOne(optional = false)
  private Cart cart;

  @OneToMany(mappedBy = "client")
  private List<Review> reviews;

  @OneToMany(mappedBy = "client")
  private List<Complaint> complaints;

  @OneToMany(mappedBy = "client")
  private List<Order> orders;
}
