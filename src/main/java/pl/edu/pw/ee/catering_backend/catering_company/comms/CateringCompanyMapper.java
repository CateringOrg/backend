package pl.edu.pw.ee.catering_backend.catering_company.comms;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pl.edu.pw.ee.catering_backend.catering_company.comms.dtos.AddCateringCompanyDTO;
import pl.edu.pw.ee.catering_backend.catering_company.domain.CateringCompany;
import pl.edu.pw.ee.catering_backend.infrastructure.db.CateringCompanyDb;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CateringCompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "NIP", source = "nip")
//  @Mapping(target = "meals", ignore = true)
    CateringCompany toCateringCompany(AddCateringCompanyDTO dto);

    @Mapping(target = "NIP", source = "nip")
    CateringCompany mapToDomain(
            CateringCompanyDb cateringCompanyDb);

    @Mapping(target = "nip", source = "NIP")
    CateringCompanyDb mapToDb(
            CateringCompany cateringCompany);
}
