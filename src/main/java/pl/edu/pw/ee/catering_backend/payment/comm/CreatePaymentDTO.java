package pl.edu.pw.ee.catering_backend.payment.comm;

import lombok.Value;

import java.util.UUID;

public record CreatePaymentDTO(UUID orderId, String paymentMethod) {
}