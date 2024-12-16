package pl.edu.pw.ee.catering_backend.catering_company.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.catering_backend.catering_company.comms.CateringCompanyMapper;
import pl.edu.pw.ee.catering_backend.catering_company.comms.IManagerCateringCompanyData;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.AddCateringCompanyDTO;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.GetCateringCompanyDTO;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompaniesManagementService;

import java.util.List;
import java.util.UUID;

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

    @Override
    @GetMapping
    @Operation(summary = "Get list of catering companies")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of companies retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<List<GetCateringCompanyDTO>> getCateringCompanies() {
        var companies = cateringCompaniesManagementService.getAllCompanies();

        return ResponseEntity.ok(companies);
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get catering company by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Company retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    public ResponseEntity<GetCateringCompanyDTO> getCateringCompanyById(@PathVariable UUID id) {
        var company = cateringCompaniesManagementService.getCompanyById(id);

        return ResponseEntity.ok(company);
    }
}
