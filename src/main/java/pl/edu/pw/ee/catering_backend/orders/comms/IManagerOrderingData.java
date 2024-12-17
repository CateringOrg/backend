package pl.edu.pw.ee.catering_backend.orders.comms;

import org.springframework.http.ResponseEntity;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.AddOrderDTO;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.OrderDto;

import java.util.List;

public interface IManagerOrderingData {
    ResponseEntity<Void> addOrder(AddOrderDTO dto);

    ResponseEntity<List<OrderDto>> getOrders();

    ResponseEntity<OrderDto> getOrderById(String id);
}
