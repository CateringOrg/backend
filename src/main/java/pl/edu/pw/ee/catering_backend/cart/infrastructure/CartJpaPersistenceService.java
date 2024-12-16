package pl.edu.pw.ee.catering_backend.cart.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.catering_backend.cart.comms.CartMapper;
import pl.edu.pw.ee.catering_backend.cart.domain.ICartPersistenceService;
import pl.edu.pw.ee.catering_backend.infrastructure.db.CartDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.CartRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.ClientRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository;
import pl.edu.pw.ee.catering_backend.offers.comms.MealMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class CartJpaPersistenceService implements ICartPersistenceService {

    private final CartRepository cartRepository;
    private final MealRepository mealRepository;
    private final CartMapper cartMapper;
    private final MealMapper mealMapper;
    private final ClientRepository clientRepository;

    @Override
    public pl.edu.pw.ee.catering_backend.cart.domain.Cart save(pl.edu.pw.ee.catering_backend.cart.domain.Cart cart) {

        CartDb dbCart = cartMapper.mapToDb(cart);
        UserDb client = clientRepository.findByLogin(cart.getClientLogin())
            .orElseThrow(() -> new NoSuchElementException("Client not found with login: " + cart.getClientLogin()));

        dbCart.setClient(client);

        List<MealDb> managedMeals = cart.getMeals().stream()
                .map(domainMeal -> mealRepository.findById(domainMeal.getId())
                        .orElseThrow(() -> new NoSuchElementException("Meal not found with ID: " + domainMeal.getId())))
                .collect(Collectors.toList());

        dbCart.setMeals(managedMeals);

        dbCart.getMeals().forEach(meal -> {
            if (meal.getCarts() == null) {
                meal.setCarts(new ArrayList<>());
            }
            if (meal.getCarts().stream().map(CartDb::getId).noneMatch(cartId -> dbCart.getId().equals(cartId))) {
                meal.getCarts().add(dbCart);
            }
        });

        CartDb savedDbCart = cartRepository.save(dbCart);

        return cartMapper.mapToDomain(savedDbCart);
    }


    @Override
    public Optional<pl.edu.pw.ee.catering_backend.cart.domain.Cart> get(UUID cartId) {
        return cartRepository.findById(cartId)
                .map(cartMapper::mapToDomain)
                .or(() -> {
                    throw new NoSuchElementException("Cart with ID " + cartId + " not found");
                });
    }


    @Override
    public Optional<pl.edu.pw.ee.catering_backend.cart.domain.Cart> getByClientLogin(String clientLogin) {
        return cartRepository.findByClient_Login(clientLogin)
                .map(cartMapper::mapToDomain);
    }
}
