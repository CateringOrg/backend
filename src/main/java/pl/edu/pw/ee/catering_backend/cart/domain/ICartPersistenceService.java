package pl.edu.pw.ee.catering_backend.cart.domain;

import java.util.Optional;
import java.util.UUID;

public interface ICartPersistenceService {

    Cart save(Cart cart);

    Optional<Cart> get(UUID cartId);

    Optional<Cart> getByClientLogin(String clientLogin);
}
