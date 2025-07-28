package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.FeedbackSaveRequest;
import ir.maktab.homeservice.dto.FeedbackResponse;
import ir.maktab.homeservice.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;


    //✅
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<FeedbackResponse> submitFeedback(
            @RequestBody @Valid
            FeedbackSaveRequest request) {
        return ResponseEntity.ok(feedbackService.submitFeedback(request));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_SPECIALIST')")
    @GetMapping("/offer-id/{offerId}")
    public ResponseEntity<Integer> feedbackRangeForSpecialistToOffer(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(feedbackService.feedbackRangeForSpecialistToOffer(
                offerId));
    }
}
