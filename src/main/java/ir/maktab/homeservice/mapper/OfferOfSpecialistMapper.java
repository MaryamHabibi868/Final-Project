package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OfferOfSpecialistMapper {

    OfferOfSpecialistRequest offerOfSpecialistMapToDTO(OfferOfSpecialist offerOfSpecialist);

    OfferOfSpecialist offerOfSpecialistDTOMapToEntity(
            OfferOfSpecialistRequest offerOfSpecialistRequest);

    OfferOfSpecialist responseMapToEntity(OfferOfSpecialistResponse offerOfSpecialistResponse);

    OfferOfSpecialistResponse entityMapToResponse(OfferOfSpecialist offerOfSpecialist);
}

