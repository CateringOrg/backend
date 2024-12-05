package pl.edu.pw.ee.catering_backend.orders.application

data class OrderCreationRequest(
    val clientLogin: String,
    val mealsIds: List<String>,
    val deliveryAddress: String,
    val deliveryMethod: String,
    val status: String
)
