package pl.edu.pw.ee.catering_backend.orders.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.ClientRepository
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository
import pl.edu.pw.ee.catering_backend.orders.data.OrdersService
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
class CreateOrderUseCase(
    @Autowired private val ordersService: OrdersService,
    @Autowired private val mealsRepository: MealRepository,
    @Autowired private val clientRepository: ClientRepository,
) {
    operator fun invoke(
        clientLogin: String,
        mealsIds: List<String>,
        deliveryAddress: String,
        deliveryMethod: String,
        status: String,
    ): Result<String> {
        val mealsUUIDs = mealsIds.map {
            try {
                UUID.fromString(it)
            } catch (e: Exception) {
                return Result.failure(Exception("Invalid meal id: $it"))
            }
        }
        val meals = mealsRepository.findAllById(mealsUUIDs)
        val client = clientRepository.findByLogin(clientLogin).getOrNull() ?: return Result.failure(Exception("Client not found"))

        if (meals.size != mealsIds.size) {
            return Result.failure(Exception("Some meals not found"))
        }

        // TODO: fix java interop
//        if (meals.any { it.available == false }) {
//            return Result.failure(Exception("Some meals are not available"))
//        }

        return ordersService.createOrder(client, meals, deliveryAddress, deliveryMethod, status)
            .map { it.toString() }

    }
}