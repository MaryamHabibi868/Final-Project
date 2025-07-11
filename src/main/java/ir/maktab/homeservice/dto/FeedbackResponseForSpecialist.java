package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponseForSpecialist {

    private Long id;

    private Integer feedbackRange;

    private Long offerOfSpecialistId;
}
