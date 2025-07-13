package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OfferSaveRequest {


    @NotNull(message = "Suggestion Price of Offer should be entered.")
    private BigDecimal suggestedPrice;

    @NotNull(message = "Start date suggestion of Offer should be entered.")
    private ZonedDateTime startDateSuggestion;

    @NotNull(message = "Task duration of Offer should be entered.")
    private Duration taskDuration;

    @NotNull
    private Long specialistId;

    @NotNull
    private Long orderId;
}
