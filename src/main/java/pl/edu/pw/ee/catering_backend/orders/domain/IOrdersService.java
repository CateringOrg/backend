package pl.edu.pw.ee.catering_backend.orders.domain;

import pl.edu.pw.ee.catering_backend.orders.comms.dtos.AddOrderDTO;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.OrderDto;

import java.util.List;
import java.util.UUID;

public interface IOrdersService {
    Order addOrder(
            AddOrderDTO addOrderDTO
    );

    List<OrderDto> getOrders();

    OrderDto getOrderById(UUID id);
}
