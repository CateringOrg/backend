package pl.edu.pw.ee.catering_backend.cart.domain;

import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.cart.comms.CartMapper;
import pl.edu.pw.ee.catering_backend.cart.comms.dtos.GetCartDTO;
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.UserRepository;
import pl.edu.pw.ee.catering_backend.offers.domain.IMealsPersistenceService;
import pl.edu.pw.ee.catering_backend.offers.domain.Meal;

@Service
@RequiredArgsConstructor
class ClientCartService implements IClientCartService {

    private final ICartPersistenceService cartPersistenceService;
  private final UserRepository userRepository;
    private final IMealsPersistenceService mealsPersistenceService;
    private final CartMapper cartMapper;

    @Override
    public void addMealToCart(String clientLogin, UUID mealId) {

      UserDb client = userRepository.findByLogin(clientLogin)
                .orElseThrow(() -> new NoSuchElementException("Client with login " + clientLogin + " not found"));

        Cart cart = cartPersistenceService.getByClientLogin(clientLogin)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setClientLogin(clientLogin);
                    newCart.validate();
                  return cartPersistenceService.save(newCart);
                });

        Meal meal = mealsPersistenceService.getById(mealId);

        cart.getMeals().add(meal);
        cart.validate();

        cartPersistenceService.save(cart);
    }

    @Override
    public GetCartDTO getCartByClientLogin(String clientLogin) {

      userRepository.findByLogin(clientLogin)
                .orElseThrow(() -> new NoSuchElementException("Client with login " + clientLogin + " not found"));

        Cart cart = cartPersistenceService.getByClientLogin(clientLogin)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setClientLogin(clientLogin);
                    newCart.validate();
                    return cartPersistenceService.save(newCart);
                });

        return cartMapper.mapToGetCartDTO(cart);
    }
}
