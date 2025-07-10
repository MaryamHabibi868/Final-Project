package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.FeedBack;
import ir.maktab.homeservice.dto.FeedbackRequest;
import ir.maktab.homeservice.dto.FeedbackResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedbackMapper {

    FeedbackRequest entityMapToRequest(FeedBack feedBack);

    FeedBack requestMapToEntity(
            FeedbackRequest feedbackRequest);

    FeedbackResponse requestMapToResponse(FeedbackRequest feedbackRequest);

    FeedbackResponse entityMapToResponse(FeedBack feedBack);
}
