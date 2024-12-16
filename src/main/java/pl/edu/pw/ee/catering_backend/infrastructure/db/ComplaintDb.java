package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Data;

@Entity(name = "complaints")
@Data
public class ComplaintDb {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String complaintReason;
  private LocalDate creationDate;

  @ManyToOne(optional = false)
  @JoinColumn(name = "client_login")
  private UserDb client;

  @ManyToOne(optional = false)
  @JoinColumn(name = "order_id")
  private OrderDb order;
}
