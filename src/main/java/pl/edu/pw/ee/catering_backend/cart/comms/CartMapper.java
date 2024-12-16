package pl.edu.pw.ee.catering_backend.cart.comms;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import pl.edu.pw.ee.catering_backend.cart.comms.dtos.GetCartDTO;
import pl.edu.pw.ee.catering_backend.cart.comms.dtos.GetCartMealDTO;
import pl.edu.pw.ee.catering_backend.cart.domain.Cart;
import pl.edu.pw.ee.catering_backend.infrastructure.db.CartDb;
import pl.edu.pw.ee.catering_backend.infrastructure.db.PhotoUrl;

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
    GetCartMealDTO mealToGetCartMealDTO(pl.edu.pw.ee.catering_backend.offers.domain.Meal meal);

    default List<String> mapPhotoUrlsToStrings(List<PhotoUrl> photoUrls) {
        if (photoUrls == null) {
            return Collections.emptyList();
        }
        return photoUrls.stream()
                .map(PhotoUrl::getUrl)
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", source="id")
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "meals", source = "meals")
    CartDb mapToDb(Cart cart);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "clientLogin", source = "client.login")
    @Mapping(target = "meals", source = "meals")
    Cart mapToDomain(CartDb dbCart);
}
