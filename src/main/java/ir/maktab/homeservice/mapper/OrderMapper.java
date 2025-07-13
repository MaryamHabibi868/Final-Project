package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.dto.OrderSaveRequest;
import ir.maktab.homeservice.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderResponse entityMapToResponse(Order order);
}
