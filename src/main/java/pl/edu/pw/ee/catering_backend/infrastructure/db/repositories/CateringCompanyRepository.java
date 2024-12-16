package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pw.ee.catering_backend.infrastructure.db.CateringCompanyDb;

public interface CateringCompanyRepository extends JpaRepository<CateringCompanyDb, UUID> {

  @Query("insert into catering_companies (id, name, nip, address) values (:id, :name, :nip, :address) on conflict (id) do update set name = :name, nip = :nip, address = :address")
  @Modifying
  @Transactional
  void saveManually(UUID id, String name, String nip, String address);
}
