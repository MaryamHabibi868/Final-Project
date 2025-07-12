package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.dto.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    AddressResponse entityMapToResponse(Address address);
}
