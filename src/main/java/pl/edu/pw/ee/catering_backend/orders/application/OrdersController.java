package pl.edu.pw.ee.catering_backend.orders.application;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.catering_backend.authentication.application.services.JwtService;
import pl.edu.pw.ee.catering_backend.orders.comms.IManagerOrderingData;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.AddOrderDTO;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.OrderDto;
import pl.edu.pw.ee.catering_backend.orders.domain.OrdersService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController implements IManagerOrderingData {

    private final JwtService jwtService;
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
    public ResponseEntity<Map<String, String>> addOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody AddOrderDTO dto
    ) {
        var login = jwtService.extractLogin(token);
        var order = ordersService.addOrder(
                login,
                dto
        );
        JsonMapper jsonMapper = new JsonMapper();
        Map<String, String> orderMap = Map.of(
                "orderId", order.getOrderId().toString()
        );
        jsonMapper.valueToTree(orderMap);
        return ResponseEntity.ok().body(orderMap);
    }

    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Orders found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Orders not found")
            }
    )
    @Override
    public ResponseEntity<List<OrderDto>> getOrders(
            @RequestHeader("Authorization") String token
    ) {
        var login = jwtService.extractLogin(token);
        var orders = ordersService.getOrders(login);
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
        var uuid = java.util.UUID.fromString(id);
        var order = ordersService.getOrderById(uuid);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }
}

