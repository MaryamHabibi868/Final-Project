package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceSaveUpdateRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HomeServiceMapper {

    HomeServiceSaveUpdateRequest mainServiceMapToDTO(HomeService homeService);

    HomeService mainServiceDTOMapToEntity(HomeServiceSaveUpdateRequest homeServiceSaveUpdateRequest);
}
