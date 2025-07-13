package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OfferMapper {

    OfferResponse entityMapToResponse(Offer offer);
}

