package pl.edu.pw.ee.catering_backend.offers.comms.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddMealDTO {
  private String description;
  private BigDecimal price;
  private Boolean available;
  private List<String> photoUrls;
}