package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
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
public class OfferOfSpecialistResponse {

    @NotNull
    private Long id;

    @NotNull
    private BigDecimal suggestedPrice;

    @NotNull
    private ZonedDateTime startDateSuggestion;

    @NotNull
    private Duration taskDuration;

    private OrderStatus orderStatus;

    @NotNull
    private Long specialistId;

    @NotNull
    private Long orderOfCustomerId;
}
