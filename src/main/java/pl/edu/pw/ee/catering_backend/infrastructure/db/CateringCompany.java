package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class CateringCompany {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String address;
  private String name;
  private String nip;

  @OneToMany(mappedBy = "cateringCompany")
  private List<Meal> meals;
}
