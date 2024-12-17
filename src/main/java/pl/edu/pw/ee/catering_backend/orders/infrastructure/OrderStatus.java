package pl.edu.pw.ee.catering_backend.orders.infrastructure;

public enum OrderStatus {
    PLACED,
    UNPAID,
    PAID,
    ACCEPTED,
    IN_PREPARATION,
    IN_DELIVERY,
    COMPLETED,
    NOT_COLLECTED,
    CANCELLED,
    REJECTED;
}
