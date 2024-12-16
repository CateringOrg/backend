package pl.edu.pw.ee.catering_backend.configuration;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompanyPersistenceService;
import pl.edu.pw.ee.catering_backend.infrastructure.db.ClientDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Wallet;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.CateringCompanyRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.ClientRepository;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialStatePreparationConfig {

    private final ICateringCompanyPersistenceService cateringCompanyPersistenceService;
    private final CateringCompanyRepository cateringCompanyRepository;
    private final ClientRepository clientRepository;
    private final MealRepository mealRepository;

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

            ClientDb client = new ClientDb();
            client.setLogin("user1");
            client.setHash("some-random-hash-value");
            Wallet wallet = new Wallet();
            wallet.setAmountOfMoney(new BigDecimal("100.00"));
            client.setWallet(wallet);
            clientRepository.save(client);
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
