package pl.edu.pw.ee.catering_backend.offers.comms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetMealDTO {
    private UUID id;
    private String description;
    private BigDecimal price;
    private String cateringCompanyName;
}
