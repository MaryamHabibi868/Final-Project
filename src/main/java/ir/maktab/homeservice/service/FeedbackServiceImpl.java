package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.FeedBack;
import ir.maktab.homeservice.domains.OfferOfSpecialist;
import ir.maktab.homeservice.dto.FeedbackRequest;
import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.FeedbackMapper;
import ir.maktab.homeservice.repository.FeedbackRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl
        extends BaseServiceImpl<FeedBack, Long, FeedbackRepository>
        implements FeedbackService {

    private final OfferOfSpecialistService offerOfSpecialistService;
    private final FeedbackMapper feedbackMapper;

    public FeedbackServiceImpl(FeedbackRepository repository,
                               OfferOfSpecialistService offerOfSpecialistService,
                               FeedbackMapper feedbackMapper) {
        super(repository);
        this.offerOfSpecialistService = offerOfSpecialistService;
        this.feedbackMapper = feedbackMapper;
    }

    //✅
    @Override
    public FeedbackResponse submitFeedback(FeedbackRequest request) {
        OfferOfSpecialist foundOfferOfSpecialist = offerOfSpecialistService.
                findById(request.getOfferOfSpecialistId());

        FeedBack feedback = new FeedBack();
        feedback.setFeedbackRange(request.getFeedbackRange());
        feedback.setFeedbackDescription(request.getFeedbackDescription());
        feedback.setOfferOfSpecialist(foundOfferOfSpecialist);
        FeedBack save = repository.save(feedback);
        return feedbackMapper.entityMapToResponse(save);
    }

    //✅
    @Override
    public Integer feedbackRangeForSpecialistToOffer(
            Long offerOfSpecialistId) {
        OfferOfSpecialist offer = offerOfSpecialistService
                .findById(offerOfSpecialistId);

        Long id = offer.getId();

        FeedBack feedBack = repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        "Feedback Not found for this offer"
                )
        );
        return feedBack.getFeedbackRange();
    }
}
