package ir.maktab.homeservice.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {


    private Long id;

    private Integer range;

    private String description;

    private Long offerId;
}
