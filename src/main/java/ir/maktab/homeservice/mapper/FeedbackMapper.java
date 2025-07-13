package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.Feedback;
import ir.maktab.homeservice.dto.FeedbackSaveRequest;
import ir.maktab.homeservice.dto.FeedbackResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedbackMapper {

    FeedbackSaveRequest entityMapToRequest(Feedback feedBack);

    Feedback requestMapToEntity(
            FeedbackSaveRequest feedbackSaveRequest);

    FeedbackResponse requestMapToResponse(FeedbackSaveRequest feedbackSaveRequest);

    FeedbackResponse entityMapToResponse(Feedback feedBack);
}
