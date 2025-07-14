package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackSaveRequest {

    @NotNull(message = "Feedback of Offer should be entered.")
    @Min (1)
    @Max (5)
    private Integer range;

    private String description;

    @NotNull
    private Long offerId;
}
