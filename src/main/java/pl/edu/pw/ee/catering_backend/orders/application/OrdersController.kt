package pl.edu.pw.ee.catering_backend.orders.application

import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.ee.catering_backend.orders.domain.CreateOrderUseCase

@RestController
@RequestMapping("/orders")
class OrdersController {
    @Autowired private lateinit var createOrderUseCase: CreateOrderUseCase

    @PostMapping("/create")
    @ApiResponse(description = "Creates order")
    @ApiResponse(responseCode = "200", description = "Order created")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    fun createOrder(@RequestBody request: OrderCreationRequest): ResponseEntity<OrderCreationResponse> {
        val result = createOrderUseCase(
            request.clientLogin,
            request.mealsIds,
            request.deliveryAddress,
            request.deliveryMethod,
            request.status
        )

        return result.fold(
            { ResponseEntity.ok(OrderCreationResponse(it)) },
            {
                ResponseEntity.badRequest().body(OrderCreationResponse(it.message ?: "Unknown error"))
            }
        )
    }
}

data class OrderCreationResponse(
    val orderId: String
)
