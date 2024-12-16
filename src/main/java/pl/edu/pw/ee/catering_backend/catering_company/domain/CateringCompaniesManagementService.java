package pl.edu.pw.ee.catering_backend.catering_company.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.catering_company.comms.CateringCompanyMapper;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.GetCateringCompanyDTO;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class CateringCompaniesManagementService implements ICateringCompaniesManagementService {

    private final ICateringCompanyPersistenceService cateringCompanyPersistenceService;
    private final CateringCompanyMapper cateringCompanyMapper;

    @Override
    public CateringCompany addNewCompany(CateringCompany company) {
        validateCompanyData(company);
        return cateringCompanyPersistenceService.save(company);
    }

    @Override
    public void validateCompanyData(CateringCompany company) {
        company.validate();
    }

    @Override
    public GetCateringCompanyDTO getCompanyById(UUID id) {
        var company = cateringCompanyPersistenceService.getById(id);
        return cateringCompanyMapper.toGetCateringCompanyDTO(company);
    }

    @Override
    public List<GetCateringCompanyDTO> getAllCompanies() {
        var companies = cateringCompanyPersistenceService.getAll();
        return companies.stream()
                .map(cateringCompanyMapper::toGetCateringCompanyDTO)
                .toList();
    }
}
