package pl.edu.pw.ee.catering_backend.orders.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.UserRepository
import pl.edu.pw.ee.catering_backend.orders.data.OrdersService
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class CreateOrderUseCase(
    @Autowired private val ordersService: OrdersService,
    @Autowired private val mealsRepository: MealRepository,
    @Autowired private val userRepository: UserRepository,
) {
    operator fun invoke(
        clientLogin: String,
        mealsIds: List<String>,
        deliveryAddress: String,
        deliveryMethod: String,
        status: String,
    ): Result<String> {
        val mealsUUIDs = mealsIds.mapNotNull {
            try {
                UUID.fromString(it)
            } catch (e: Exception) {
                null
            }
        }
        val meals = mealsRepository.findAllById(mealsUUIDs)
        val client = userRepository.findByLogin(clientLogin).getOrNull()

        if (meals.size != mealsIds.size) {
            return Result.failure(Exception("Some meals not found"))
        }

        // TODO: fix java interop
//        if (meals.any { it.available == false }) {
//            return Result.failure(Exception("Some meals are not available"))
//        }

        return ordersService.createOrder(client, meals, deliveryAddress, deliveryMethod, status)
    }
}