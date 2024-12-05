package pl.edu.pw.ee.catering_backend.configuration;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompanyPersistenceService;
import pl.edu.pw.ee.catering_backend.infrastructure.db.CateringCompany;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.CateringCompanyRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialStatePreparationConfig {

  private final ICateringCompanyPersistenceService cateringCompanyPersistenceService;
  private final CateringCompanyRepository cateringCompanyRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void initialize() {
    try {
      log.info("Running initialization...");
      cateringCompanyRepository.saveManually(UUID.fromString("12fcc746-b380-4f0b-a34c-6b110a615a94"), "Testowa firma", "5252344078", "Adres testowy 123");
      log.info("Initialization done!");
    } catch (Exception e) {
      log.warn("Exception thrown when creating new company!");
    }
  }
}
