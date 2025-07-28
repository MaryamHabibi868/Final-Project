package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.WalletResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {

    @Mapping(source = "userInformation.id", target = "userId")
    WalletResponse entityMapToWalletResponse(Wallet wallet);
}
