package pl.edu.pw.ee.catering_backend.orders.application

import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.ee.catering_backend.orders.domain.CreateOrderUseCase
import java.util.logging.Logger
import kotlin.math.log

@RestController
@RequestMapping("/orders")
class OrdersController {
    @Autowired
    private lateinit var createOrderUseCase: CreateOrderUseCase

    private val logger = Logger.getLogger(OrdersController::class.java.name)

    @PostMapping("/create")
    @ApiResponse(description = "Creates order")
    @ApiResponse(responseCode = "200", description = "Order created")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    fun createOrder(@RequestBody request: OrderCreationRequest): ResponseEntity<OrderCreationResponse> {
        if (!areAllArgumentsValid(request)) {
            logger.warning("Invalid arguments")
            return ResponseEntity.badRequest().body(OrderCreationResponse(error = "Invalid arguments"))
        }
        logger.info("Creating order")

        val result = createOrderUseCase(
            request.clientLogin,
            request.mealsIds,
            request.deliveryAddress,
            request.deliveryMethod,
            request.status
        )

        return result.fold(
            { ResponseEntity.ok(OrderCreationResponse(orderId = it)) },
            {
                ResponseEntity.badRequest().body(OrderCreationResponse(error = it.message ?: "Unknown error"))
            }
        )
    }

    private fun areAllArgumentsValid(request: OrderCreationRequest): Boolean {
        return request.clientLogin.isNotEmpty() && request.mealsIds.isNotEmpty() && request.deliveryAddress.isNotEmpty() && request.deliveryMethod.isNotEmpty() && request.status.isNotEmpty()
    }
}

data class OrderCreationResponse(
    val orderId: String? = null,
    val error: String? = null,
)
