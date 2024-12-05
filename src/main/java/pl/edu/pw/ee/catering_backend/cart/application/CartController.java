package pl.edu.pw.ee.catering_backend.cart.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.catering_backend.cart.comms.IClientCartData;
import pl.edu.pw.ee.catering_backend.cart.comms.dtos.GetCartDTO;
import pl.edu.pw.ee.catering_backend.cart.domain.IClientCartService;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController implements IClientCartData {

    private final IClientCartService clientCartService;

    @PostMapping("/{clientLogin}/addToCart/{mealId}")
    @Operation(summary = "Add a meal to the client's cart")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meal added to cart successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided for adding meal to cart"),
            @ApiResponse(responseCode = "404", description = "Client or meal not found")
    })
    public ResponseEntity<Void> addMealToCart(@PathVariable String clientLogin, @PathVariable UUID mealId) {
        clientCartService.addMealToCart(clientLogin, mealId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clientLogin}")
    @Operation(summary = "Retrieve the client's cart")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<GetCartDTO> getCartByClientLogin(@PathVariable String clientLogin) {
        GetCartDTO cart = clientCartService.getCartByClientLogin(clientLogin);
        return ResponseEntity.ok(cart);
    }
}
