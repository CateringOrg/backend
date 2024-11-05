package pl.edu.pw.ee.catering_backend.infrastructure.db;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.Data;

@Embeddable
@Data
public class Wallet {

  private BigDecimal amountOfMoney;
}
