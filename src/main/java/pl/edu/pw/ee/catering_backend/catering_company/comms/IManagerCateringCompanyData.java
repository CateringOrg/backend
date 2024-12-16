package pl.edu.pw.ee.catering_backend.catering_company.comms;

import org.springframework.http.ResponseEntity;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.AddCateringCompanyDTO;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.GetCateringCompanyDTO;

import java.util.List;
import java.util.UUID;

public interface IManagerCateringCompanyData {

    ResponseEntity<Void> addCateringCompany(AddCateringCompanyDTO dto);

    ResponseEntity<Void> validateCateringCompany(AddCateringCompanyDTO dto);

    ResponseEntity<GetCateringCompanyDTO> getCateringCompanyById(UUID id);

    ResponseEntity<List<GetCateringCompanyDTO>> getCateringCompanies();
}
