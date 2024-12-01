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
    fun createOrder(
        client: Client,
        mealsIds: List<Meal>,
        deliveryAddress: String,
        deliveryMethod: String,
        status: String,
    ): Result<Order>

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
}

@Service
class OrdersServiceImpl(
    @Autowired private val ordersRepository: OrderRepository,
    @Autowired private val mealRepository: MealRepository,
) : OrdersService {
    override fun createOrder(
        client: Client,
        mealsIds: List<Meal>,
        deliveryAddress: String,
        deliveryMethod: String,
        status: String,
    ): Result<Order> {
        val order = Order()
        order.client = client
        order.meals = mealsIds
        order.deliveryAddress = deliveryAddress
        order.deliveryMethod = deliveryMethod
        order.status = status

        try {
            ordersRepository.save(order)
            return Result.success(order)
        } catch (e: Exception) {
            return Result.failure(e)
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
                val updatedOrder = order.get()
                updatedOrder.client.login = clientLogin
                updatedOrder.meals = mealRepository.findAllById(mealsIds.map { UUID.fromString(it) })
                updatedOrder.deliveryAddress = deliveryAddress
                updatedOrder.deliveryMethod = deliveryMethod
                updatedOrder.status = status
                ordersRepository.save(updatedOrder)
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