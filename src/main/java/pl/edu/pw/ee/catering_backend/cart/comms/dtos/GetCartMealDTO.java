package pl.edu.pw.ee.catering_backend.cart.comms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetCartMealDTO {
    private UUID id;
    private String description;
    private BigDecimal price;
    private String cateringCompanyName;
    private List<String> photoUrls;
}
