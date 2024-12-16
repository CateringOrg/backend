package pl.edu.pw.ee.catering_backend.configuration;

import java.math.BigDecimal;
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
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Wallet;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.CateringCompanyRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.UserRepository;
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

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        try {
            log.info("Running initialization...");

            List.of("ADMIN", "CATERING", "CLIENT").forEach(role -> {
                User user = User.builder().login(role.toLowerCase() + "user")
                    .hash(passwordEncoder.encode("1234")).role(AppRole.valueOf(role)).build();
                userPersistenceService.save(user);
            });

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
            userRepository.save(client);

            log.info("Client initialized!");

            MealDb meal = new MealDb();
            meal.setId(UUID.fromString("ed5c57b7-890e-4102-a8ff-b264fa29f02b"));
            meal.setDescription("meal1");
            meal.setPrice(BigDecimal.ZERO);
            meal.setAvailable(true);
            meal.setCateringCompany(cateringCompanyRepository.findById(
                    UUID.fromString("12fcc746-b380-4f0b-a34c-6b110a615a94")).orElseThrow());
            var m = mealRepository.save(meal);
            log.info("Meal initialized {}!", m.getId());

            log.info("Initialization done!");
        } catch (Exception e) {
            log.warn("Exception thrown during initialization!", e);
        }

    }
}
