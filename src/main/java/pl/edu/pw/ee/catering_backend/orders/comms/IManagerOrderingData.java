package pl.edu.pw.ee.catering_backend.orders.comms;

import org.springframework.http.ResponseEntity;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.AddOrderDTO;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.OrderDto;

import java.util.List;
import java.util.Map;

public interface IManagerOrderingData {
    ResponseEntity<Map<String, String>> addOrder(
            String token,
            AddOrderDTO dto
    );

    ResponseEntity<List<OrderDto>> getOrders(
            String token
    );

    ResponseEntity<OrderDto> getOrderById(String id);
}
