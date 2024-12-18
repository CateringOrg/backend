package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<MealDb> meals;

  @ManyToOne(optional = false)
  @JoinColumn(name = "client_login")
  private UserDb client;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<ComplaintDb> complaint;
}
