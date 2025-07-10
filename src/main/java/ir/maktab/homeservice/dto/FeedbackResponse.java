package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {

    @NotNull
    private Long id;

    @NotNull
    private Integer feedbackRange;

    private String feedbackType;

    @NotNull
    private Long offerOfSpecialistId;
}
