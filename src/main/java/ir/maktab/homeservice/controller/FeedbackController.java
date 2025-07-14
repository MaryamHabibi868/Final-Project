package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.FeedbackSaveRequest;
import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;


    @PostMapping
    public ResponseEntity<FeedbackResponse> submitFeedback(
            @RequestBody @Valid
            FeedbackSaveRequest request) {
        return ResponseEntity.ok(feedbackService.submitFeedback(request));
    }


    @GetMapping("/offer-id/{offerId}")
    public ResponseEntity<Integer> feedbackRangeForSpecialistToOffer(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(feedbackService.feedbackRangeForSpecialistToOffer(
                offerId));
    }
}
