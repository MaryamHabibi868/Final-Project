package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Feedback;
import ir.maktab.homeservice.dto.FeedbackResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedbackMapper {

    @Mapping(source = "offer.id", target = "offerId")
    FeedbackResponse entityMapToResponse(Feedback feedBack);
}
