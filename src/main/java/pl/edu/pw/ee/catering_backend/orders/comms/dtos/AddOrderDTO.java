package pl.edu.pw.ee.catering_backend.orders.comms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AddOrderDTO {
    String clientLogin;
    String deliveryAddress;
    String deliveryMethod;
    List<UUID> mealIds;
    LocalDateTime deliveryTime;
}
