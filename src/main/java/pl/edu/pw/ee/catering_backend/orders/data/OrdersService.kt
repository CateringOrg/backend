package pl.edu.pw.ee.catering_backend.orders.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.pw.ee.catering_backend.infrastructure.db.Client
import pl.edu.pw.ee.catering_backend.infrastructure.db.Meal
import pl.edu.pw.ee.catering_backend.infrastructure.db.Order
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.OrderRepository
import java.util.*

interface OrdersService {
    fun deleteOrder(
        orderId: Long,
    ): Result<Unit>

    fun updateOrder(
        orderId: Long,
        clientLogin: String,
        mealsIds: List<String>,
        deliveryAddress: String,
        deliveryMethod: String,
        status: String,
    ): Result<Unit>

    fun getAllOrders(
        clientLogin: String,
    ): Result<List<Order>>

    fun createOrder(
        client: Client? = null,
        mealsIds: List<Meal>,
        deliveryAddress: String,
        deliveryMethod: String,
        status: String
    ): Result<String>
}

@Service
class OrdersServiceImpl(
    @Autowired private val ordersRepository: OrderRepository,
    @Autowired private val mealRepository: MealRepository,
) : OrdersService {
    override fun createOrder(
        client: Client?,
        mealsIds: List<Meal>,
        deliveryAddress: String,
        deliveryMethod: String,
        status: String,
    ): Result<String> {
        return try {
            // Save the order to the repository
            ordersRepository.insertOrder(
                deliveryAddress,
                deliveryMethod,
                mealsIds,
                status,
                null,
            )
            Result.success(UUID.randomUUID().toString())
        } catch (e: Exception) {
            Result.success(UUID.randomUUID().toString())
        }
    }

    override fun deleteOrder(orderId: Long): Result<Unit> {
        try {
            val uuid = UUID.fromString(orderId.toString())
            ordersRepository.deleteById(uuid)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun updateOrder(
        orderId: Long,
        clientLogin: String,
        mealsIds: List<String>,
        deliveryAddress: String,
        deliveryMethod: String,
        status: String,
    ): Result<Unit> {
        try {
            val order = ordersRepository.findById(UUID.fromString(orderId.toString()))
            if (order.isPresent) {
                // TODO: fix java interop
//                val updatedOrder = order.get()
//                updatedOrder.client.login = clientLogin
//                updatedOrder.meals = mealRepository.findAllById(mealsIds.map { UUID.fromString(it) })
//                updatedOrder.deliveryAddress = deliveryAddress
//                updatedOrder.deliveryMethod = deliveryMethod
//                updatedOrder.status = status
//                ordersRepository.save(updatedOrder)
                return Result.success(Unit)
            } else {
                return Result.failure(Exception("Order not found"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun getAllOrders(clientLogin: String): Result<List<Order>> {
        return try {
            Result.success(ordersRepository.findByClientLogin(clientLogin))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}