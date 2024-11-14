package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class PhotoUrl {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  private String url;

  @ManyToOne(optional = false)
  private MealDb meal;
}
