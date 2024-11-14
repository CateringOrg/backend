package pl.edu.pw.ee.catering_backend.catering_company.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.catering_backend.catering_company.comms.CateringCompanyMapper;
import pl.edu.pw.ee.catering_backend.catering_company.comms.IManagerCateringCompanyData;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.AddCateringCompanyDTO;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompaniesManagementService;

@RestController
@RequestMapping("/catering-companies")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class CateringCompaniesController implements IManagerCateringCompanyData {

  private final ICateringCompaniesManagementService cateringCompaniesManagementService;
  private final CateringCompanyMapper cateringCompanyMapper;

  @Override
  @PostMapping
  @Operation(summary = "Add new catering company")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Company added successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid company data")
  })
  public ResponseEntity<Void> addCateringCompany(@RequestBody AddCateringCompanyDTO dto) {
    cateringCompaniesManagementService.addNewCompany(
        cateringCompanyMapper.toCateringCompany(dto));
    return ResponseEntity.noContent().build();
  }

  @Override
  @PutMapping
  @Operation(summary = "Validate catering company data")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Company data validated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid company data")
  })
  public ResponseEntity<Void> validateCateringCompany(@RequestBody AddCateringCompanyDTO dto) {
    cateringCompaniesManagementService.validateCompanyData(
        cateringCompanyMapper.toCateringCompany(dto));
    return ResponseEntity.noContent().build();
  }
}
