package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.dto.OrderSaveRequest;
import ir.maktab.homeservice.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(source = "address.id"    , target = "addressId")
    @Mapping(source = "homeService.id", target = "homeServiceId")
    @Mapping(source = "customer.id"   , target = "customerId")
    OrderResponse entityMapToResponse(Order order);
}
