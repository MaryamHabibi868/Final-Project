package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Manager;
import ir.maktab.homeservice.dto.ManagerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ManagerMapper {

    @Mapping(source = "wallet.id", target = "walletId")
    ManagerResponse entityMapToResponse(Manager manager);

}
