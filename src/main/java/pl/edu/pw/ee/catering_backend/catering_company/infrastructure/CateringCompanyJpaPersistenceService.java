package pl.edu.pw.ee.catering_backend.catering_company.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.catering_company.domain.CateringCompany;
import pl.edu.pw.ee.catering_backend.catering_company.domain.ICateringCompanyPersistenceService;
import pl.edu.pw.ee.catering_backend.infrastructure.db.CateringCompanyDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.CateringCompanyRepository;

@RequiredArgsConstructor
@Service
class CateringCompanyJpaPersistenceService implements ICateringCompanyPersistenceService {

  private final CateringCompanyRepository cateringCompanyRepository;
  private final CateringCompanyDbMapper cateringCompanyDbMapper;

  @Override
  public CateringCompany save(CateringCompany cateringCompany) {
    return cateringCompanyDbMapper.mapToDomain(
        cateringCompanyRepository.save(cateringCompanyDbMapper.mapToDb(cateringCompany)));
  }

  @Override
  public CateringCompany get(UUID id) {
    return cateringCompanyDbMapper.mapToDomain(
        cateringCompanyRepository.findById(id).orElseThrow());
  }

  @Mapper(componentModel = ComponentModel.SPRING)
  interface CateringCompanyDbMapper {

    @Mapping(target = "NIP", source = "nip")
    CateringCompany mapToDomain(
        CateringCompanyDb cateringCompanyDb);

    @Mapping(target = "nip", source = "NIP")
    CateringCompanyDb mapToDb(
        CateringCompany cateringCompany);
  }
}
