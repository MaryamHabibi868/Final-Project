package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.dto.FeedbackSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackServiceImplTest {

    private FeedbackService service;
    private FeedbackResponse response;
    private FeedbackSaveRequest request;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(FeedbackService.class);
        response = Mockito.mock(FeedbackResponse.class);
        request = Mockito.mock(FeedbackSaveRequest.class);
    }


    @Test
    void submitFeedback() {
        Mockito.when(service.submitFeedback(request)).thenReturn(response);
        assertEquals(response, service.submitFeedback(request));
    }

    @Test
    void feedbackRangeForSpecialistToOffer() {
        Mockito.when(service.feedbackRangeForSpecialistToOffer(Mockito.anyLong()))
                .thenReturn(5);
        int result = service.feedbackRangeForSpecialistToOffer(1L);
        assertEquals(5, result);

    }
}