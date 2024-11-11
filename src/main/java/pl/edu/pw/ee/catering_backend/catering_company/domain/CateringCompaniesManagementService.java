package pl.edu.pw.ee.catering_backend.catering_company.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CateringCompaniesManagementService implements ICateringCompaniesManagementService {

  private final ICateringCompanyPersistenceService cateringCompanyPersistenceService;

  @Override
  public CateringCompany addNewCompany(CateringCompany company) {
    validateCompanyData(company);
    return cateringCompanyPersistenceService.save(company);
  }

  @Override
  public void validateCompanyData(CateringCompany company) {
    company.validate();
  }
}
