package pl.edu.pw.ee.catering_backend.catering_company.infrastructure;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.catering_company.domain.CateringCompany;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompanyPersistenceService;
import pl.edu.pw.ee.catering_backend.catering_company.comms.CateringCompanyMapper;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.CateringCompanyRepository;

@RequiredArgsConstructor
@Service
class CateringCompanyJpaPersistenceService implements ICateringCompanyPersistenceService {

  private final CateringCompanyRepository cateringCompanyRepository;
  private final CateringCompanyMapper cateringCompanyMapper;

  @Override
  public CateringCompany save(CateringCompany cateringCompany) {
    return cateringCompanyMapper.mapToDomain(
            cateringCompanyRepository.save(cateringCompanyMapper.mapToDb(cateringCompany))
    );
  }

  @Override
  public CateringCompany getById(UUID id) {
    return cateringCompanyMapper.mapToDomain(
            cateringCompanyRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Catering company not found with ID: " + id))
    );
  }

  @Override
  public List<CateringCompany> getAll() {
    return cateringCompanyRepository.findAll().stream()
            .map(cateringCompanyMapper::mapToDomain)
            .toList();
  }
}
