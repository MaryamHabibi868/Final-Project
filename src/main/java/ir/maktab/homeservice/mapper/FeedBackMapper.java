package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.FeedBack;
import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.FeedbackSubmit;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedBackMapper {

    FeedbackSubmit feedbackMapToDTO(FeedBack feedBack);

    FeedBack feedbackDTOMapToEntity(
            FeedbackSubmit feedbackSubmit);
}
