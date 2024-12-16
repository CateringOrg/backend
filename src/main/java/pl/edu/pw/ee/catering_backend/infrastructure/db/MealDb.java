package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Entity(name = "meals")
@Data
@ToString(exclude = {"photoUrls", "carts", "orders", "cateringCompany", "reviews"})
public class MealDb {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Boolean available;

  private String name;

  private String description;

  @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PhotoUrl> photoUrls;

  private BigDecimal price;

  @ManyToMany
  @JoinTable(name = "meal_carts",
      joinColumns = @JoinColumn(name = "meal_id"),
      inverseJoinColumns = @JoinColumn(name = "cart_id")
  )
  private List<CartDb> carts;

  @ManyToMany
  @JoinTable(name = "meal_orders",
      joinColumns = @JoinColumn(name = "meal_id"),
      inverseJoinColumns = @JoinColumn(name = "order_id")
  )
  private List<OrderDb> orders;

  @ManyToOne(optional = false)
  @JoinColumn(name = "catering_company_id")
  private CateringCompanyDb cateringCompany;

  @OneToMany(mappedBy = "meal")
  private List<ReviewDb> reviews;
}
