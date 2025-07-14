package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.HomeServiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HomeServiceMapper {

    @Mapping(source = "parentService.id", target = "parentServiceId")
    HomeServiceResponse entityMapToResponse(HomeService homeService);

}
