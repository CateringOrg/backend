package pl.edu.pw.ee.catering_backend.offers.comms;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import pl.edu.pw.ee.catering_backend.catering_company.comms.CateringCompanyMapper;
import pl.edu.pw.ee.catering_backend.infrastructure.db.MealDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.PhotoUrl;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.AddMealDTO;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;
import pl.edu.pw.ee.catering_backend.offers.domain.Meal;

@Mapper(componentModel = ComponentModel.SPRING, uses = {CateringCompanyMapper.class})
public interface MealMapper {

    @Mapping(target = "cateringCompanyName", source = "cateringCompany.name")
    @Mapping(target = "photoUrls", source = "photoUrls")
    GetMealDTO mapToGetMealDTO(Meal meal);

    default List<String> mapPhotoUrlsToStrings(List<PhotoUrl> photoUrls) {
        if (photoUrls == null) {
            return Collections.emptyList();
        }
        return photoUrls.stream()
                .map(PhotoUrl::getUrl)
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", source="id")
    @Mapping(target = "available", constant = "true")
    MealDb mapToDb(Meal meal);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cateringCompany", ignore = true)
    Meal mapToDomain(AddMealDTO addMealDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "photoUrls", source = "photoUrls")
    @Mapping(target = "cateringCompany", source = "cateringCompany")
    Meal mapToDomain(MealDb dbMeal);

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
