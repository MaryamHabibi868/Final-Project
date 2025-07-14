package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Feedback;
import ir.maktab.homeservice.dto.FeedbackSaveRequest;
import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.service.base.BaseService;

public interface FeedbackService
        extends BaseService<Feedback, Long> {

    // ☑️ final check
    //✅
    FeedbackResponse submitFeedback(FeedbackSaveRequest request);

    // ☑️ final check
    //✅
    Integer feedbackRangeForSpecialistToOffer(
            Long offerId);
}
