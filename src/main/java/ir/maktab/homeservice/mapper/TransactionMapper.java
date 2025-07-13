package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.dto.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    @Mapping(source = "wallet.id", target = "walletId")
    TransactionResponse entityMapToResponse(Transaction entity);
}
