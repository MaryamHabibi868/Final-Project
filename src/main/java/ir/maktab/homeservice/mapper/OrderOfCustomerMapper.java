package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.dto.OrderOfCustomerRequest;
import ir.maktab.homeservice.dto.OrderOfCustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderOfCustomerMapper {

    OrderOfCustomerRequest orderOfCustomerMapToDTO(OrderOfCustomer orderOfCustomer);

    OrderOfCustomer orderOfCustomerDTOMapToEntity(
            OrderOfCustomerRequest orderOfCustomerRequest);

    OrderOfCustomer requestMapToEntity(OrderOfCustomerRequest orderOfCustomerRequest);

    OrderOfCustomer responseMapToEntity(OrderOfCustomerResponse orderOfCustomerResponse);

    OrderOfCustomerResponse entityMapToResponse(OrderOfCustomer orderOfCustomer);
}
