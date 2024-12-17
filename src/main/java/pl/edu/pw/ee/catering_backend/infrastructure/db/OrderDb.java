package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import pl.edu.pw.ee.catering_backend.orders.infrastructure.OrderStatus;

@Entity(name = "orders")
@Data
public class OrderDb {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String deliveryAddress;
  private String deliveryMethod;
  private LocalDateTime deliveryTime;
  private LocalDateTime orderCreationTime;
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @ManyToMany(mappedBy = "orders")
  private List<MealDb> meals;

  @ManyToOne(optional = false)
  @JoinColumn(name = "client_login")
  private UserDb client;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
  private List<ComplaintDb> complaint;
}
