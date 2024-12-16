package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Entity(name = "carts")
@Data
@ToString(exclude = {"meals", "client"})
public class CartDb {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToMany(mappedBy = "carts")
  private List<MealDb> meals;

  @OneToOne(optional = true)
  @JoinColumn(name = "client_login")
  private ClientDb client;
}
