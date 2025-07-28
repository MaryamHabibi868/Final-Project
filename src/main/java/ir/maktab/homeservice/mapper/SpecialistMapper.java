package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.AddRemoveSToHResponse;
import ir.maktab.homeservice.dto.ScoreResponse;
import ir.maktab.homeservice.dto.SpecialistResponse;
import ir.maktab.homeservice.dto.VerifiedUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialistMapper {

    @Mapping(source = "wallet.id", target = "walletId")
    SpecialistResponse entityMapToResponse(Specialist specialist);

    VerifiedUserResponse entityMapToVerifiedUserResponse(Specialist specialist);


    AddRemoveSToHResponse entityMapToAddRemoveSToHResponse(Specialist specialist);


    ScoreResponse entityMapToScoreResponse(Specialist specialist);
}
