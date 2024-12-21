package pl.edu.pw.ee.catering_backend.payment.comm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IPaymentSystem {
    ResponseEntity<String> makePayment(
            String token,
            CreatePaymentDTO createPaymentDTO
    );
}
