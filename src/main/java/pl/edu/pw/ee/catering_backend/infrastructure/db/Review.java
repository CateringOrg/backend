package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String comment;
  private Integer rate;

  @ManyToOne(optional = false)
  private MealDb meal;

  @ManyToOne(optional = false)
  private Client client;
}
