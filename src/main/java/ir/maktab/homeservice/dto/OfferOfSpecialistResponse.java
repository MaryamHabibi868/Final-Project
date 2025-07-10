package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OfferOfSpecialistResponse {

    @NotNull
    private Long id;

    @NotNull
    private BigDecimal suggestedPrice;

    @NotBlank
    private ZonedDateTime startDateSuggestion;

    @NotNull
    private Double taskDuration;

    @NotNull
    private Long specialistId;

    @NotNull
    private Long orderOfCustomerId;
}
