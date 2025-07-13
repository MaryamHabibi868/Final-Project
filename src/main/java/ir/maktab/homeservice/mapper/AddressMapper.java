package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.dto.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    @Mapping(source = "customer.id", target = "customerId")
    AddressResponse entityMapToResponse(Address address);
}
