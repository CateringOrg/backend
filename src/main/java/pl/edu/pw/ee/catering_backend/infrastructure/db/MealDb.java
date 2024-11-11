package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class MealDb {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Boolean available;

  private String description;

  @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PhotoUrl> photoUrls;

  private BigDecimal price;

  @ManyToMany
  private List<Cart> carts;

  @ManyToMany
  private List<Order> orders;

  @ManyToOne(optional = false)
  private CateringCompanyDb cateringCompany;

  @OneToMany
  private List<Review> reviews;
}
