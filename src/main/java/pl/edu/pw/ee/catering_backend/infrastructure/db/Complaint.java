package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
public class Complaint {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String complaintReason;
  private LocalDate creationDate;

  @ManyToOne(optional = false)
  private Client client;

  @ManyToOne(optional = false)
  private Order order;
}
