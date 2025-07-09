package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerUpdateRequest customerMapToDTO(Customer customer);

    Customer customerDTOMapToEntity(CustomerUpdateRequest customerUpdateRequest);

    Customer foundCustomerToEntity(CustomerResponse customerResponse);

    CustomerResponse entityMapToResponse(Customer customer);

    Customer responseMapToEntity(CustomerResponse customerResponse);
}
