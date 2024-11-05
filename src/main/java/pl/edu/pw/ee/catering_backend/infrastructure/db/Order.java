package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne(optional = false)
  private Complaint complaint;
}
