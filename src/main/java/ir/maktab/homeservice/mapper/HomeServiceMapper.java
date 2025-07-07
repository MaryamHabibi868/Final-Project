package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceFound;
import ir.maktab.homeservice.dto.HomeServiceSaveUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HomeServiceMapper {

    HomeServiceSaveUpdateRequest homeServiceMapToDTO(HomeService homeService);

    HomeService homeServiceDTOMapToEntity(HomeServiceSaveUpdateRequest homeServiceSaveUpdateRequest);

    HomeService foundHomeServiceToEntity(HomeServiceFound homeServiceFound);
}
