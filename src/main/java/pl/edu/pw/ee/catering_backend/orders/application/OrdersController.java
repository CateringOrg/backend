package pl.edu.pw.ee.catering_backend.orders.application;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.catering_backend.orders.comms.IManagerOrderingData;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.AddOrderDTO;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.OrderDto;
import pl.edu.pw.ee.catering_backend.orders.domain.OrdersService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController implements IManagerOrderingData {

    private final OrdersService ordersService;

    @Override
    @PostMapping("/create")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Order created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "409", description = "Order already exists")
            }
    )
    public ResponseEntity<Void> addOrder(@RequestBody AddOrderDTO dto) {
        var order = ordersService.addOrder(dto);
        return ResponseEntity.created(URI.create("/orders/" + order.getOrderId())).build();
    }

    @GetMapping
    @Override
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Orders found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Orders not found")
            }
    )
    public ResponseEntity<List<OrderDto>> getOrders() {
        var orders = ordersService.getOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @Override
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Order found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    public ResponseEntity<OrderDto> getOrderById(@PathVariable String id) {
        try {
            var uuid = java.util.UUID.fromString(id);
            var order = ordersService.getOrderById(uuid);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

