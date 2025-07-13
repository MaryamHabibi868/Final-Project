package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Feedback;
import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.dto.FeedbackSaveRequest;
import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.FeedbackMapper;
import ir.maktab.homeservice.repository.FeedbackRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl
        extends BaseServiceImpl<Feedback, Long, FeedbackRepository>
        implements FeedbackService {

    private final OfferService offerService;
    private final FeedbackMapper feedbackMapper;

    public FeedbackServiceImpl(FeedbackRepository repository,
                               OfferService offerService,
                               FeedbackMapper feedbackMapper) {
        super(repository);
        this.offerService = offerService;
        this.feedbackMapper = feedbackMapper;
    }

    //✅
    @Override
    public FeedbackResponse submitFeedback(FeedbackSaveRequest request) {
        Offer foundOffer = offerService.
                findById(request.getOfferId());

        Feedback feedback = new Feedback();
        feedback.setRange(request.getRange());
        if (request.getDescription() != null) {
            feedback.setDescription(request.getDescription());
        }
        feedback.setOffer(foundOffer);
        Feedback save = repository.save(feedback);
        return feedbackMapper.entityMapToResponse(save);
    }

    //✅
    @Override
    public Integer feedbackRangeForSpecialistToOffer(
            Long offerId) {
        Offer offer = offerService
                .findById(offerId);

        Long id = offer.getId();

        Feedback feedBack = repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        "Feedback Not found for this offer"
                )
        );
        return feedBack.getRange();
    }
}
