package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.MainService;
import ir.maktab.homeservice.dto.MainServiceSaveUpdateRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MainServiceMapper {

    MainServiceSaveUpdateRequest mainServiceMapToDTO(MainService mainService);

    MainService MainServiceDTOMapToMainService(MainServiceSaveUpdateRequest mainServiceSaveUpdateRequest);
}
