package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Feedback;
import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.dto.FeedbackResponseForSpecialist;
import ir.maktab.homeservice.dto.FeedbackSaveRequest;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.mapper.FeedbackMapper;
import ir.maktab.homeservice.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private OfferService offerService;

    @Mock
    private FeedbackMapper feedbackMapper;

    @Mock
    private SpecialistService specialistService;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void submitFeedback_shouldSaveAndReturnFeedbackResponse_whenOfferIsPaid() {
        FeedbackSaveRequest request = new FeedbackSaveRequest();
        request.setOfferId(1L);
        request.setRange(4);
        request.setDescription("Great service");

        Offer offer = new Offer();
        offer.setId(1L);
        offer.setStatus(OfferStatus.PAID);
        Specialist specialist = new Specialist();
        specialist.setId(2L);
        offer.setSpecialist(specialist);

        Feedback feedback = new Feedback();
        feedback.setRange(request.getRange());
        feedback.setDescription(request.getDescription());
        feedback.setOffer(offer);

        Feedback savedFeedback = new Feedback();
        savedFeedback.setId(1L);
        savedFeedback.setRange(4);
        savedFeedback.setDescription("Great service");

        FeedbackResponse response = new FeedbackResponse();
        response.setId(1L);

        when(offerService.findById(1L)).thenReturn(offer);
        when(feedbackRepository.save(any(Feedback.class)))
                .thenReturn(savedFeedback);
        when(feedbackRepository.findAverageRangeBySpecialistId(2L))
                .thenReturn(4.5);
        when(feedbackMapper.entityMapToResponse(savedFeedback))
                .thenReturn(response);

        FeedbackResponse result = feedbackService.submitFeedback(request);

        assertEquals(1L, result.getId());
        verify(specialistService).save(specialist);
    }

    @Test
    void submitFeedback_shouldThrowException_whenOfferIsNotPaid() {
        FeedbackSaveRequest request = new FeedbackSaveRequest();
        request.setOfferId(1L);
        request.setRange(4);

        Offer offer = new Offer();
        offer.setStatus(OfferStatus.PENDING);

        when(offerService.findById(1L)).thenReturn(offer);

        assertThrows(NotApprovedException.class,
                () -> feedbackService.submitFeedback(request));
    }

    @Test
    void feedbackRangeForSpecialistToOffer_shouldReturnFeedbackResponse() {
        Long offerId = 1L;
        Feedback feedback = new Feedback();
        FeedbackResponseForSpecialist response = new FeedbackResponseForSpecialist();
        response.setRange(4);

        when(feedbackRepository.findByOfferId(offerId))
                .thenReturn(feedback);
        when(feedbackMapper.entityMapToResponseForSpecialist(feedback))
                .thenReturn(response);

        FeedbackResponseForSpecialist result = feedbackService
                .feedbackRangeForSpecialistToOffer(offerId);

        assertEquals(4, result.getRange());
    }
}
