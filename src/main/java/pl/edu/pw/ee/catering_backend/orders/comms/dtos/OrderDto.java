package pl.edu.pw.ee.catering_backend.orders.comms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderDto {
    UUID orderId;
    String clientLogin;
    String deliveryAddress;
    String deliveryMethod;
    String status;
    // TODO consult whether it's better to user GetMealDTO from offers module or create a new one
    List<GetMealDTO> meals;
    LocalDateTime deliveryTime;
    LocalDateTime orderCreationTime;
}
