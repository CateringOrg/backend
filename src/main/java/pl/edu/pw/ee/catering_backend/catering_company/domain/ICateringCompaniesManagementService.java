package pl.edu.pw.ee.catering_backend.catering_company.domain;

import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.GetCateringCompanyDTO;

import java.util.List;
import java.util.UUID;

public interface ICateringCompaniesManagementService {

  CateringCompany addNewCompany(CateringCompany company);

  void validateCompanyData(CateringCompany company);

  GetCateringCompanyDTO getCompanyById(UUID id);

  List<GetCateringCompanyDTO> getAllCompanies();
}
