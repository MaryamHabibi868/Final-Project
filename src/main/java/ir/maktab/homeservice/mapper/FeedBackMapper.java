package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.domains.FeedBack;
import ir.maktab.homeservice.dto.FeedbackRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedBackMapper {

    FeedbackRequest feedbackMapToDTO(FeedBack feedBack);

    FeedBack feedbackDTOMapToEntity(
            FeedbackRequest feedbackRequest);
}
