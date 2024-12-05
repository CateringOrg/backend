package pl.edu.pw.ee.catering_backend.cart.comms;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import pl.edu.pw.ee.catering_backend.cart.comms.dtos.GetCartDTO;

public interface IClientCartData {
    ResponseEntity<Void> addMealToCart(String clientLogin, UUID mealId);

    ResponseEntity<GetCartDTO> getCartByClientLogin(String clientLogin);
}
