package pl.edu.pw.ee.catering_backend.configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pw.ee.catering_backend.infrastructure.db.*;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.*;
import pl.edu.pw.ee.catering_backend.orders.comms.OrderMapper;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.AddOrderDTO;
import pl.edu.pw.ee.catering_backend.orders.domain.Order;
import pl.edu.pw.ee.catering_backend.orders.domain.OrdersService;
import pl.edu.pw.ee.catering_backend.payment.comm.CreatePaymentDTO;
import pl.edu.pw.ee.catering_backend.payment.domain.IPaymentService;
import pl.edu.pw.ee.catering_backend.user.domain.AppRole;
import pl.edu.pw.ee.catering_backend.user.domain.User;
import pl.edu.pw.ee.catering_backend.user.domain.UserPersistenceService;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialStatePreparationConfig {

    private final CateringCompanyRepository cateringCompanyRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserPersistenceService userPersistenceService;
    private final OrdersService ordersService;
    private final IPaymentService paymentService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        try {
            log.info("Running initialization...");

            cateringCompanyRepository.saveManually(
                    UUID.fromString("12fcc746-b380-4f0b-a34c-6b110a615a94"),
                    "Testowa firma",
                    "5252344078",
                    "Adres testowy 123"
            );
            log.info("Catering company initialized!");

            UserDb client = new UserDb();
            client.setLogin("user1");
            client.setHash("some-random-hash-value");
            Wallet wallet = new Wallet();
            wallet.setAmountOfMoney(new BigDecimal("100.00"));
            client.setWallet(wallet);
            client.setRole(AppRole.CLIENT);
            UserDb createdUser = userRepository.save(client);

            log.info("Client initialized {}!", createdUser.getLogin());

            MealDb meal = new MealDb();
            meal.setId(UUID.fromString("ed5c57b7-890e-4102-a8ff-b264fa29f02b"));
            meal.setName("meal 1");
            meal.setDescription("meal 1 desc");
            meal.setPrice(BigDecimal.valueOf(10.00));
            meal.setAvailable(true);
            meal.setPhotoUrls(List.of(
                    new PhotoUrl( "https://a.allegroimg.com/original/12e55e/13fd8e1543269cf3d7ab771bfcaa", meal)
            ));
            meal.setCateringCompany(cateringCompanyRepository.findById(
                    UUID.fromString("12fcc746-b380-4f0b-a34c-6b110a615a94")).orElseThrow());
            var m = mealRepository.save(meal);
            log.info("Meal initialized {}!", m.getId());

            Map.of("ADMIN", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQAM-uuObFYoZgurQT8Vvl3oHXnnWC6EzIKaQ&s",
                    "CATERING", "https://www.miastokobiet.pl/wp-content/uploads/2018/03/zdrowy-posilek.jpg",
                    "CLIENT", "https://i0.wp.com/psychiatraplus.pl/wp-content/uploads/2024/07/Smieciowe-jedzenie-a-mozg-scaled.jpg?resize=696%2C464&ssl=1")
                    .forEach(this::createTestUser);
            List<UserDb> users = userRepository.findAll();

            log.info("Test users initialized, credentials{}", users.stream().map(UserDb::getLogin).toList());
            log.info("Initialization done!");
        } catch (Exception e) {
            log.warn("Exception thrown during initialization!", e);
        }

    }

    private void createTestUser(String role, String url) {
        Wallet wallet = new Wallet();
        wallet.setAmountOfMoney(new BigDecimal("100.00"));
        AppRole appRole = AppRole.valueOf(role);
        String login = appRole.toString().toLowerCase() + "_user";
        User user = User.builder()
                .login(login)
                .hash(passwordEncoder.encode("1234"))
                .role(appRole)
                .wallet(wallet)
                .build();
        userPersistenceService.save(user);

        MealDb meal = new MealDb();
        meal.setName("Test meal for user " + user.getLogin());
        meal.setDescription("Test meal description for user " + user.getLogin());
        meal.setPrice(BigDecimal.valueOf(RandomUtils.nextDouble(10.0, 50.0)));
        meal.setAvailable(true);
        meal.setPhotoUrls(List.of(
                new PhotoUrl( url, meal)
        ));
        meal.setCateringCompany(cateringCompanyRepository.findById(
                UUID.fromString("12fcc746-b380-4f0b-a34c-6b110a615a94")).orElseThrow());

        MealDb savedMeal = mealRepository.save(meal);

        AddOrderDTO firstAddOrderDto = new AddOrderDTO(
                "First order delivery address for user " + user.getLogin(),
                "First order delivery method for user " + user.getLogin(),
                List.of(savedMeal.getId()),
                LocalDateTime.now().plusDays(2)
        );
        AddOrderDTO secondAddOrderDto = new AddOrderDTO(
                "Second order delivery address for user " + user.getLogin(),
                "Second order delivery method for user " + user.getLogin(),
                List.of(savedMeal.getId()),
                LocalDateTime.now().plusDays(1)
        );
        AddOrderDTO thirdAddOrderDto = new AddOrderDTO(
                "Third order delivery address for user " + user.getLogin(),
                "Third order delivery method for user " + user.getLogin(),
                List.of(savedMeal.getId()),
                LocalDateTime.now().plusDays(2)
        );

        ordersService.addOrder(
                user.getLogin(),
                firstAddOrderDto
        );
        ordersService.addOrder(
                user.getLogin(),
                secondAddOrderDto
        );
        ordersService.addOrder(
                user.getLogin(),
                thirdAddOrderDto
        );

        List<OrderDb> addedOrders = orderRepository.findAll().stream().filter(orderDb -> orderDb.getClient().getLogin().equals(user.getLogin())).toList();

        Order randomOrder = addedOrders.stream().map(orderMapper::mapDbToDomainModel).toList().getFirst();

        CreatePaymentDTO createPaymentDTO = new CreatePaymentDTO(
                randomOrder.getOrderId(),
                user.getLogin()
        );
        paymentService.payForOrder(
                createPaymentDTO.orderId(),
                user.getLogin()
        );

        log.info("Test user created with order and payment, credentials: {} password: 1234, order id: {}", user.getLogin(), randomOrder.getOrderId());
    }

}
