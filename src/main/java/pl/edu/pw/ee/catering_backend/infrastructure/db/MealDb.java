package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

  @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<OrderDb> orders;

  @ManyToOne(optional = false)
  @JoinColumn(name = "catering_company_id")
  private CateringCompanyDb cateringCompany;

  @OneToMany(mappedBy = "meal")
  private List<ReviewDb> reviews;
}
