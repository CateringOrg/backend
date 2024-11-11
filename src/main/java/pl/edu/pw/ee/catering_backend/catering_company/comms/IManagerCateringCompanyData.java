package pl.edu.pw.ee.catering_backend.catering_company.comms;

import org.springframework.http.ResponseEntity;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.AddCateringCompanyDTO;

public interface IManagerCateringCompanyData {

  ResponseEntity<Void> addCateringCompany(AddCateringCompanyDTO dto);

  ResponseEntity<Void> validateCateringCompany(AddCateringCompanyDTO dto);
}
