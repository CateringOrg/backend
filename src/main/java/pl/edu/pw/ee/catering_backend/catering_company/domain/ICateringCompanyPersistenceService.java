package pl.edu.pw.ee.catering_backend.catering_company.domain;

import java.util.List;
import java.util.UUID;

public interface ICateringCompanyPersistenceService {

  CateringCompany save(CateringCompany cateringCompany);

  CateringCompany getById(UUID id);

  List<CateringCompany> getAll();
}
