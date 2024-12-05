package pl.edu.pw.ee.catering_backend.cart.comms;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import pl.edu.pw.ee.catering_backend.cart.comms.dtos.GetCartDTO;
import pl.edu.pw.ee.catering_backend.cart.comms.dtos.GetCartMealDTO;
import pl.edu.pw.ee.catering_backend.cart.domain.Cart;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Meal;
import pl.edu.pw.ee.catering_backend.infrastructure.db.PhotoUrl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CartMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "clientLogin", source = "clientLogin")
    @Mapping(target = "meals", source = "meals")
    GetCartDTO mapToGetCartDTO(Cart cart);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "cateringCompanyName", source = "cateringCompany.name")
    @Mapping(target = "photoUrls", source = "photoUrls")
    GetCartMealDTO mapToGetCartMealDTO(Meal meal);

    default List<String> mapPhotoUrlsToStrings(List<PhotoUrl> photoUrls) {
        if (photoUrls == null) {
            return Collections.emptyList();
        }
        return photoUrls.stream()
                .map(PhotoUrl::getUrl)
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "meals", source = "meals")
    pl.edu.pw.ee.catering_backend.infrastructure.db.Cart mapToDb(Cart cart);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "clientLogin", source = "client.login")
    @Mapping(target = "meals", source = "meals")
    Cart mapToDomain(pl.edu.pw.ee.catering_backend.infrastructure.db.Cart dbCart);


    default List<GetCartMealDTO> mapMealsToGetCartMealDTOs(List<Meal> meals) {
        if (meals == null) {
            return Collections.emptyList();
        }
        return meals.stream()
                .map(this::mapToGetCartMealDTO)
                .collect(Collectors.toList());
    }

    default List<Meal> mapGetCartMealDTOsToMeals(List<GetCartMealDTO> mealDTOs) {
        if (mealDTOs == null) {
            return Collections.emptyList();
        }
        return mealDTOs.stream()
                .map(dto -> {
                    Meal meal = new Meal();
                    meal.setId(dto.getId());
                    meal.setDescription(dto.getDescription());
                    meal.setPrice(dto.getPrice());
                    // CateringCompany i PhotoUrls muszą być mapowane osobno w razie potrzeby
                    return meal;
                })
                .collect(Collectors.toList());
    }
}
