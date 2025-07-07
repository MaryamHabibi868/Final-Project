package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.dto.OrderOfCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderOfCustomerMapper {

    OrderOfCustomerRequest orderOfCustomerMapToDTO(OrderOfCustomer orderOfCustomer);

    OrderOfCustomer orderOfCustomerDTOMapToEntity(
            OrderOfCustomerRequest orderOfCustomerRequest);}
