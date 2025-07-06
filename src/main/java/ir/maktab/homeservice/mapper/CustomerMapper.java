package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerSaveUpdateRequest customerMapToDTO(Customer customer);

    Customer customerDTOMapToEntity(CustomerSaveUpdateRequest customerSaveUpdateRequest);

    Customer foundCustomerToEntity(CustomerFound customerFound);
}
