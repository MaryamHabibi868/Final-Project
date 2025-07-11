package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.FeedbackRequest;
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

    //✅
    @PostMapping
    public ResponseEntity<FeedbackResponse> submitFeedback(
            @RequestBody @Valid
            FeedbackRequest feedbackRequest) {
        return ResponseEntity.ok(feedbackService.submitFeedback(feedbackRequest));
    }

    //✅
    @GetMapping("/offer-of-specialist-id/{offerOfSpecialistId}")
    public ResponseEntity<Integer> feedbackRangeForSpecialistToOffer(
            @PathVariable Long offerOfSpecialistId) {
        return ResponseEntity.ok(feedbackService.feedbackRangeForSpecialistToOffer(
                offerOfSpecialistId));
    }
}
