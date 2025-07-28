package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    @Mapping(source = "wallet.id", target = "walletId")
    CustomerResponse entityMapToResponse(Customer customer);

    VerifiedUserResponse entityMapToVerifiedUserResponse(Customer customer);
}
