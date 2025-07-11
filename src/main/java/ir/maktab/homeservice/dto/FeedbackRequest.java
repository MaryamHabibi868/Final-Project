package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {

    @NotNull(message = "Feedback of Offer should be entered.")
    @Size (min =1, max =5)
    private Integer feedbackRange;

    private String feedbackDescription;

    @NotNull
    private Long offerOfSpecialistId;
}
