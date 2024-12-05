package pl.edu.pw.ee.catering_backend.orders.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.ClientRepository
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository
import pl.edu.pw.ee.catering_backend.orders.data.OrdersService
import java.util.UUID

@Service
class CreateOrderUseCase(
    @Autowired private val ordersService: OrdersService,
    @Autowired private val mealsRepository: MealRepository,
    @Autowired private val clientRepository: ClientRepository
) {
    operator fun invoke(
        clientLogin: String,
        mealsIds: List<String>,
        deliveryAddress: String,
        deliveryMethod: String,
        status: String
    ) : Result<String> {
        val mealsUUIDs = mealsIds.map { UUID.fromString(it) }
        val meals = mealsRepository.findAllById(mealsUUIDs)
        val client = clientRepository.findByLogin(clientLogin) ?: return Result.failure(Exception("Client not found"))

        if (meals.size != mealsIds.size) {
            return Result.failure(Exception("Some meals not found"))
        }

        if (meals.any { it.available == false }) {
            return Result.failure(Exception("Some meals are not available"))
        }

        if (client.size != 1) {
            return Result.failure(Exception("Client not found"))
        }

        return ordersService.createOrder(client.first(), meals, deliveryAddress, deliveryMethod, status).map { it.id.toString() }

    }
}