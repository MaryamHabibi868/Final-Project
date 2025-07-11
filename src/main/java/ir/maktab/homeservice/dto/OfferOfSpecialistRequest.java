package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.OfferOfSpecialist;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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
public class OfferOfSpecialistRequest {


    @NotNull(message = "Suggestion Price of Offer should be entered.")
    private BigDecimal suggestedPrice;

    @NotNull(message = "Start date suggestion of Offer should be entered.")
    private ZonedDateTime startDateSuggestion;

    @NotNull(message = "Task duration of Offer should be entered.")
    private Duration taskDuration;

    @NotNull
    private Long specialistId;

    @NotNull
    private Long orderOfCustomerId;
}
