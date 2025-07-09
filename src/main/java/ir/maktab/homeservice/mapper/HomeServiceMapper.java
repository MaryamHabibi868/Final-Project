package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HomeServiceMapper {

    HomeServiceSaveRequest homeServiceMapToDTO(HomeService homeService);

    HomeService homeServiceDTOMapToEntity(HomeServiceSaveRequest homeServiceSaveRequest);

    HomeService foundHomeServiceToEntity(HomeServiceUpdateRequest homeServiceUpdateRequest);

    HomeService responseMapToEntity(HomeServiceResponse homeServiceResponse);

    HomeServiceResponse entityMapToResponse(HomeService homeService);

    HomeService saveRequestMapToEntity(HomeServiceSaveRequest homeServiceSaveRequest);

    HomeService updateRequestMapToEntity(HomeServiceUpdateRequest homeServiceUpdateRequest);
}
