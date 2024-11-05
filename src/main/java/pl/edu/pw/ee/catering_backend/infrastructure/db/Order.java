package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String deliveryAddress;
  private String deliveryMethod;
  private String status;

  @ManyToMany
  private List<Meal> meals;

  @ManyToOne(optional = false)
  private Client client;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Complaint> complaint;
}
