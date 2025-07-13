package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.SpecialistLoginRequest;
import ir.maktab.homeservice.dto.SpecialistResponse;
import ir.maktab.homeservice.dto.SpecialistSaveRequest;
import ir.maktab.homeservice.dto.SpecialistUpdateInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialistMapper {


    SpecialistResponse entityMapToResponse(Specialist specialist);
}
