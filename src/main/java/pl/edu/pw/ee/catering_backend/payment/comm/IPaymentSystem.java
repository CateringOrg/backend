package pl.edu.pw.ee.catering_backend.payment.comm;

import org.springframework.http.ResponseEntity;

public interface IPaymentSystem {
    ResponseEntity<String> makePayment(CreatePaymentDTO createPaymentDTO);
}
