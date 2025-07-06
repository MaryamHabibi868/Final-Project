package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.dto.SpecialistFound;
import ir.maktab.homeservice.dto.SpecialistSaveUpdateRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialistMapper {

    SpecialistSaveUpdateRequest specialistMapToDTO(Specialist specialist);

    Specialist specialistDTOMapToEntity(SpecialistSaveUpdateRequest specialistSaveUpdateRequest);

    Specialist foundSpecialistToEntity(SpecialistFound specialistFound);
}
