package pl.edu.pw.ee.catering_backend.offers.comms;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import pl.edu.pw.ee.catering_backend.catering_company.comms.CateringCompanyMapper;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Meal;
import pl.edu.pw.ee.catering_backend.infrastructure.db.PhotoUrl;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = ComponentModel.SPRING, uses = {CateringCompanyMapper.class})
public interface MealMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "available", constant = "true")
  Meal mapToDb(pl.edu.pw.ee.catering_backend.offers.domain.Meal meal);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "cateringCompany", ignore = true)
  pl.edu.pw.ee.catering_backend.offers.domain.Meal mapToDomain(AddMealDTO addMealDTO);

  @Mapping(target = "cateringCompany", ignore = true)
  pl.edu.pw.ee.catering_backend.offers.domain.Meal mapToDomain(Meal meal);

  default List<PhotoUrl> mapPhotoUrls(List<String> urls) {
    if (urls == null) {
      return Collections.emptyList();
    }
    return urls.stream()
            .map(url -> {
              PhotoUrl photoUrl = new PhotoUrl();
              photoUrl.setUrl(url);
              return photoUrl;
            })
            .collect(Collectors.toList());
  }
}
