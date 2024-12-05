package pl.edu.pw.ee.catering_backend.cart.comms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pw.ee.catering_backend.cart.comms.dtos.GetCartMealDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetCartDTO {
    private UUID id;
    private String clientLogin;
    private List<GetCartMealDTO> meals;
}
