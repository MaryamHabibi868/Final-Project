package ir.maktab.homeservice.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponseForSpecialist {

    private Long id;

    private Integer range;

    private Long offerId;
}
