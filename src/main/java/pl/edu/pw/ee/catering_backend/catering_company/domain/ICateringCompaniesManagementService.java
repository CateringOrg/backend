package pl.edu.pw.ee.catering_backend.catering_company.domain;


public interface ICateringCompaniesManagementService {

  CateringCompany addNewCompany(CateringCompany company);

  void validateCompanyData(CateringCompany company);
}
