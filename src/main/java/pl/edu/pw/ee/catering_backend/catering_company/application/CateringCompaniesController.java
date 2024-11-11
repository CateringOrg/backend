package pl.edu.pw.ee.catering_backend.catering_company.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.catering_backend.catering_company.comms.IManagerCateringCompanyData;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.AddCateringCompanyDTO;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.CateringCompanyDtoMapper;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompaniesManagementService;

@RestController
@RequestMapping("/catering-companies")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class CateringCompaniesController implements IManagerCateringCompanyData {

  private final ICateringCompaniesManagementService cateringCompaniesManagementService;
  private final CateringCompanyDtoMapper cateringCompanyDtoMapper;

  @Override
  @PostMapping
  public ResponseEntity<Void> addCateringCompany(@RequestBody AddCateringCompanyDTO dto) {
    cateringCompaniesManagementService.addNewCompany(
        cateringCompanyDtoMapper.toCateringCompany(dto));
    return ResponseEntity.noContent().build();
  }

  @Override
  @PutMapping
  public ResponseEntity<Void> validateCateringCompany(@RequestBody AddCateringCompanyDTO dto) {
    cateringCompaniesManagementService.validateCompanyData(
        cateringCompanyDtoMapper.toCateringCompany(dto));
    return ResponseEntity.noContent().build();
  }
}
