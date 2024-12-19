package pl.edu.pw.ee.catering_backend.configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompanyPersistenceService;
import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.OrderDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Wallet;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.CateringCompanyRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.OrderRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.UserRepository;
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

    private final ICateringCompanyPersistenceService cateringCompanyPersistenceService;
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
            meal.setCateringCompany(cateringCompanyRepository.findById(
                    UUID.fromString("12fcc746-b380-4f0b-a34c-6b110a615a94")).orElseThrow());
            var m = mealRepository.save(meal);
            log.info("Meal initialized {}!", m.getId());

            List.of("ADMIN", "CATERING", "CLIENT").forEach(this::createTestUser);

            List<UserDb> users = userRepository.findAll();

            log.info("Test users initialized, credentials" + users.stream().map(UserDb::getLogin).toList());
            log.info("Initialization done!");
        } catch (Exception e) {
            log.warn("Exception thrown during initialization!", e);
        }

    }

    private void createTestUser(String role) {
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
        meal.setName("test meal- cheap");
        meal.setDescription("test meal desc");
        meal.setPrice(BigDecimal.valueOf(10.00));
        meal.setAvailable(true);
        meal.setCateringCompany(cateringCompanyRepository.findById(
                UUID.fromString("12fcc746-b380-4f0b-a34c-6b110a615a94")).orElseThrow());

        mealRepository.save(meal);

        AddOrderDTO firstAddOrderDto = new AddOrderDTO(
                user.getLogin(),
                "mock delivery address",
                "mock delivery method",
                List.of(meal.getId()),
                LocalDateTime.now().plusDays(2)
        );
        AddOrderDTO secondAddOrderDto = new AddOrderDTO(
                user.getLogin(),
                "mock delivery address",
                "mock delivery method",
                List.of(meal.getId()),
                LocalDateTime.now().plusDays(1)
        );
        AddOrderDTO thirdAddOrderDto = new AddOrderDTO(
                user.getLogin(),
                "mock delivery address",
                "mock delivery method",
                List.of(meal.getId()),
                LocalDateTime.now().plusDays(2)
        );

        ordersService.addOrder(firstAddOrderDto);
        ordersService.addOrder(secondAddOrderDto);
        ordersService.addOrder(thirdAddOrderDto);

        List<OrderDb> addedOrders = orderRepository.findAll().stream().filter(orderDb -> orderDb.getClient().getLogin().equals(user.getLogin())).toList();

        Order randomOrder = addedOrders.stream().map(orderMapper::mapDbToDomainModel).toList().getFirst();

        CreatePaymentDTO createPaymentDTO = new CreatePaymentDTO(
                randomOrder.getOrderId(),
                user.getLogin(),
                "1234",
                user.getLogin()
        );
        paymentService.payForOrder(createPaymentDTO);

        log.info("Test user created with order and payment, credentials: client" + user.getLogin() + " password: 1234, order id: " + randomOrder.getOrderId());
    }
}
