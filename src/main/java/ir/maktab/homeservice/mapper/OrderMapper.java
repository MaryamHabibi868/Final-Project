package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.dto.OrderResponseForManager;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.dto.OrderSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressMapper.class , HomeServiceMapper.class , CustomerMapper.class})
public interface OrderMapper {

    @Mapping(source = "address.id"    , target = "addressId")
    @Mapping(source = "homeService.id", target = "homeServiceId")
    @Mapping(source = "customer.id"   , target = "customerId")
    OrderResponse entityMapToResponse(Order order);


    @Mapping(source = "order.id"    , target = "orderId")
    OrderResponseForManager entityMapToResponseForManager(Order order);


    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "homeService.title", target = "homeServiceTitle")
    @Mapping(source = "customer.lastName", target = "customerName")
    OrderSummaryResponse entityMapToSummaryResponse(Order order);


    default OrderResponse entityMapToResponseByFilter(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .description(order.getDescription())
                .suggestedPrice(order.getSuggestedPrice())
                .startDate(order.getStartDate())
                .homeServiceId(order.getHomeService().getId())
                .customerId(order.getCustomer().getId())
                .build();
    }
}