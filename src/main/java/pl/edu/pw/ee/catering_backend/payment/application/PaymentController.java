package pl.edu.pw.ee.catering_backend.payment.application;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.catering_backend.authentication.application.services.JwtService;
import pl.edu.pw.ee.catering_backend.payment.comm.CreatePaymentDTO;
import pl.edu.pw.ee.catering_backend.payment.comm.IPaymentSystem;
import pl.edu.pw.ee.catering_backend.payment.domain.IPaymentService;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController implements IPaymentSystem {
    private final JwtService jwtService;

    private final IPaymentService paymentService;

    @Override
    @PostMapping("/payForOrder")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Payment successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "409", description = "Payment failed")
            }
    )
    public ResponseEntity<String> makePayment(
            @RequestHeader("Authorization") String token,
            @RequestBody CreatePaymentDTO createPaymentDTO
    ) {
        final String login = jwtService.extractLogin(token);

        final boolean paymentSuccessful = paymentService.payForOrder(
                createPaymentDTO.orderId(),
                login
        );

        if (!paymentSuccessful) {
            throw new IllegalArgumentException("Payment failed");
        } else {
            return ResponseEntity.ok().build();
        }

    }
}