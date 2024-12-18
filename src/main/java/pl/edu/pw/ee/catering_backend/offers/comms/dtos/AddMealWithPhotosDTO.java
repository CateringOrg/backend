package pl.edu.pw.ee.catering_backend.offers.comms.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddMealWithPhotosDTO {
  private String name;
  private String description;
  private BigDecimal price;
  private Boolean available;
  private List<MultipartFile> photos;
}
