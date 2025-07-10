package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.FeedBack;
import ir.maktab.homeservice.dto.FeedbackRequest;
import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.service.base.BaseService;

public interface FeedbackService
        extends BaseService<FeedBack, Long> {

    //✅
    FeedbackResponse submitFeedback(FeedbackRequest request);
}
