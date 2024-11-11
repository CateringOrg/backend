package pl.edu.pw.ee.catering_backend.catering_company.comms.dtos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pw.ee.catering_backend.catering_company.domain.CateringCompany;

@Mapper(componentModel = "spring")
public interface CateringCompanyDtoMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "NIP", source = "nip")
//  @Mapping(target = "meals", ignore = true)
  CateringCompany toCateringCompany(AddCateringCompanyDTO dto);
}
