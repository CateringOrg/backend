package pl.edu.pw.ee.catering_backend.orders.domain;

import jakarta.transaction.Transactional;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.AddOrderDTO;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.OrderDto;

import java.util.List;
import java.util.UUID;

public interface IOrdersService {
    Order addOrder(
            String userLogin,
            AddOrderDTO addOrderDTO
    );

    List<OrderDto> getOrders(
            String userLogin
    );

    OrderDto getOrderById(UUID id);
}
