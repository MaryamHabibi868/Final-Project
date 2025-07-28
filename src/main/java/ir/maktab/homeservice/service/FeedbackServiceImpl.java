package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Feedback;
import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.dto.FeedbackResponseForSpecialist;
import ir.maktab.homeservice.dto.FeedbackSaveRequest;
import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.exception.NotApprovedException;
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
    private final SpecialistService specialistService;

    public FeedbackServiceImpl(FeedbackRepository repository,
                               OfferService offerService,
                               FeedbackMapper feedbackMapper,
                               SpecialistService specialistService) {
        super(repository);
        this.offerService = offerService;
        this.feedbackMapper = feedbackMapper;
        this.specialistService = specialistService;
    }


    @Override
    public FeedbackResponse submitFeedback(FeedbackSaveRequest request) {
        Offer foundOffer = offerService.
                findById(request.getOfferId());

        if (foundOffer.getStatus() == OfferStatus.PAID) {

            Feedback feedback = new Feedback();
            feedback.setRange(request.getRange());
            if (request.getDescription() != null) {
                feedback.setDescription(request.getDescription());
            }
            feedback.setOffer(foundOffer);
            Feedback save = repository.save(feedback);

            Specialist foundSpecialist = foundOffer.getSpecialist();
            Double averageRangeBySpecialistId = repository.
                    findAverageRangeBySpecialistId(foundSpecialist.getId());

            foundSpecialist.setScore(averageRangeBySpecialistId);
            specialistService.save(foundSpecialist);

            return feedbackMapper.entityMapToResponse(save);
        }
        else {
            throw new NotApprovedException("This offer is not paid yet");
        }
    }


    @Override
    public FeedbackResponseForSpecialist feedbackRangeForSpecialistToOffer(
            Long offerId) {
        Feedback feedback = repository.findByOfferId(offerId);

        return feedbackMapper.entityMapToResponseForSpecialist(feedback);
    }
}
