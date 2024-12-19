package pl.edu.pw.ee.catering_backend.payment.application;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.catering_backend.payment.comm.CreatePaymentDTO;
import pl.edu.pw.ee.catering_backend.payment.comm.IPaymentSystem;
import pl.edu.pw.ee.catering_backend.payment.domain.IPaymentService;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController implements IPaymentSystem {

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
    public ResponseEntity<String> makePayment(@RequestBody CreatePaymentDTO createPaymentDTO) {
        try {
            final boolean paymentSuccessful = paymentService.payForOrder(createPaymentDTO);

            if (!paymentSuccessful) {
                return ResponseEntity.status(409).build();
            } else {
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}