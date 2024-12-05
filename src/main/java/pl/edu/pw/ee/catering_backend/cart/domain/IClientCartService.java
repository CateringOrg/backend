package pl.edu.pw.ee.catering_backend.cart.domain;

import pl.edu.pw.ee.catering_backend.cart.comms.dtos.GetCartDTO;

import java.util.UUID;

public interface IClientCartService {
    void addMealToCart(String clientLogin, UUID mealId);

    GetCartDTO getCartByClientLogin(String clientLogin);
}
