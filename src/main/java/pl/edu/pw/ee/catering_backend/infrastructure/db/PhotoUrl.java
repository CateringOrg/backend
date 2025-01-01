package pl.edu.pw.ee.catering_backend.infrastructure.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "photos_urls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoUrl {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(columnDefinition = "TEXT")
  private String url;

  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(name = "meal_id")
  private MealDb meal;
  
  public PhotoUrl(String url, MealDb meal) {
    this.url = url;
    this.meal = meal;
  }
}
