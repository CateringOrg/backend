package pl.edu.pw.ee.catering_backend.payment.comm;

import lombok.Value;

import java.util.UUID;

@Value
public class CreatePaymentDTO {
    UUID orderId;
    String login;
    String password;
    String paymentMethod;
}