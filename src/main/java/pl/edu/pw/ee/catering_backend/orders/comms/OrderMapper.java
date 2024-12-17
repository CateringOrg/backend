package pl.edu.pw.ee.catering_backend.orders.comms;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pl.edu.pw.ee.catering_backend.infrastructure.db.OrderDb;
import pl.edu.pw.ee.catering_backend.offers.comms.MealMapper;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.AddOrderDTO;
import pl.edu.pw.ee.catering_backend.orders.comms.dtos.OrderDto;
import pl.edu.pw.ee.catering_backend.orders.domain.Order;
import pl.edu.pw.ee.catering_backend.user.infrastructure.UserDbMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MealMapper.class, UserDbMapper.class})
public interface OrderMapper {
    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    @Mapping(target = "deliveryMethod", source = "deliveryMethod")
    @Mapping(target = "deliveryTime", source = "deliveryTime")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "meals", ignore = true)
    @Mapping(target = "client", ignore = true)
    Order mapDtoToDomainModel(AddOrderDTO dto);

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    @Mapping(target = "deliveryMethod", source = "deliveryMethod")
    @Mapping(target = "deliveryTime", source = "deliveryTime")
    @Mapping(target = "orderCreationTime", source = "orderCreationTime")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "meals", source = "meals")
    @Mapping(target = "client", source = "client")
    Order mapDbToDomainModel(OrderDb orderDb);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    @Mapping(target = "deliveryMethod", source = "deliveryMethod")
    @Mapping(target = "deliveryTime", source = "deliveryTime")
    @Mapping(target = "orderCreationTime", source = "orderCreationTime")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "clientLogin", source = "client.login")
    @Mapping(target = "meals", source = "meals")
    OrderDto mapToOrderDTO(Order order);

//    @Mapping(target = "id", source = "id")
    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    @Mapping(target = "deliveryMethod", source = "deliveryMethod")
    @Mapping(target = "deliveryTime", source = "deliveryTime")
    @Mapping(target = "orderCreationTime", source = "orderCreationTime")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "meals", source = "meals")
    @Mapping(target = "client", source = "client")
    OrderDb mapToOrderDb(Order order);
}
