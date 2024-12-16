package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Entity(name = "orders")
@Data
public class OrderDb {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String deliveryAddress;
  private String deliveryMethod;
  private String status;

  @ManyToMany(mappedBy = "orders")
  private List<MealDb> meals;

  @ManyToOne(optional = false)
  @JoinColumn(name = "client_login")
  private ClientDb client;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
  private List<ComplaintDb> complaint;
}
