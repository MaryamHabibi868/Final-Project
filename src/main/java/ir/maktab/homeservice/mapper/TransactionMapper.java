package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.dto.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    TransactionResponse entityMapToResponse(Transaction entity);
}
