package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Feedback;
import ir.maktab.homeservice.domains.Offer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.dto.FeedbackSaveRequest;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.mapper.FeedbackMapper;
import ir.maktab.homeservice.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceImplTest {

    private FeedbackRepository feedbackRepository;
    private OfferService offerService;
    private FeedbackMapper feedbackMapper;
    private SpecialistService specialistService;
    private FeedbackServiceImpl feedbackService;

    @BeforeEach
    void setUp() {
        feedbackRepository = mock(FeedbackRepository.class);
        offerService = mock(OfferService.class);
        feedbackMapper = mock(FeedbackMapper.class);
        specialistService = mock(SpecialistService.class);

        feedbackService = new FeedbackServiceImpl(
                feedbackRepository,
                offerService,
                feedbackMapper,
                specialistService);
    }

    @Test
    void submitFeedback_offerIsPaid_savesFeedbackAndUpdatesSpecialistScore() {

        FeedbackSaveRequest request = new FeedbackSaveRequest();
        request.setOfferId(1L);
        request.setRange(4);
        request.setDescription("Good job");

        Offer offer = new Offer();
        offer.setId(1L);
        offer.setStatus(OfferStatus.PAID);

        Specialist specialist = new Specialist();
        specialist.setId(2L);
        specialist.setScore(3.5);
        offer.setSpecialist(specialist);

        when(offerService.findById(1L)).thenReturn(offer);

        Feedback savedFeedback = new Feedback();
        savedFeedback.setId(10L);
        savedFeedback.setRange(4);
        savedFeedback.setDescription("Good job");
        savedFeedback.setOffer(offer);

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(savedFeedback);
        when(feedbackMapper.entityMapToResponse(savedFeedback)).thenReturn(
                new FeedbackResponse(10L, 4, "Good job", 1L));

        when(feedbackRepository.findAverageRangeBySpecialistId(2L)).thenReturn(4.2);

        FeedbackResponse response = feedbackService.submitFeedback(request);

        assertNotNull(response);
        assertEquals(4, response.getRange());
        assertEquals("Good job", response.getDescription());


        verify(specialistService, times(1)).save(specialist);
        assertEquals(4.2, specialist.getScore());
    }

    @Test
    void submitFeedback_offerNotPaid_shouldThrowException() {
        FeedbackSaveRequest request = new FeedbackSaveRequest();
        request.setOfferId(1L);
        request.setRange(3);

        Offer offer = new Offer();
        offer.setStatus(OfferStatus.DONE);

        when(offerService.findById(1L)).thenReturn(offer);

        NotApprovedException exception = assertThrows(NotApprovedException.class, () -> {
            feedbackService.submitFeedback(request);
        });

        assertEquals("This offer is not paid yet", exception.getMessage());
    }

    @Test
    void feedbackRangeForSpecialistToOffer_returnsRange() {
        Long offerId = 1L;

        Offer offer = new Offer();
        offer.setId(offerId);

        Feedback feedback = new Feedback();
        feedback.setRange(5);

        when(offerService.findById(offerId)).thenReturn(offer);
        when(feedbackRepository.findByOfferId(offerId)).thenReturn(feedback);

        /*Integer range = feedbackService.feedbackRangeForSpecialistToOffer(offerId);*/

        /*assertEquals(5, range);*/
    }
}
