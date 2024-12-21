package pl.edu.pw.ee.catering_backend.payment.domain;

import java.util.UUID;

public interface IPaymentService {
    boolean payForOrder(
            UUID orderId,
            String login
    );
}