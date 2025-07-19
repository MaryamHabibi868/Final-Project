package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(source = "address.id"    , target = "addressId")
    @Mapping(source = "homeService.id", target = "homeServiceId")
    @Mapping(source = "customer.id"   , target = "customerId")
    OrderResponse entityMapToResponse(Order order);


    default OrderResponse entityMapToResponseByFilter(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .description(order.getDescription())
                .suggestedPrice(order.getSuggestedPrice())
                .startDate(order.getStartDate())
                .homeServiceId(order.getHomeService().getId())
                .customerId(order.getCustomer().getId())
                .build();
    };
}