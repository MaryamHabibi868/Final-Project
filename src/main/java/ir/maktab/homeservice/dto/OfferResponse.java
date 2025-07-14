package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponse {


    private Long id;

    private BigDecimal suggestedPrice;

    private ZonedDateTime startDateSuggestion;

    private Duration taskDuration;

    private OfferStatus status;

    private Long specialistId;

    private Long orderId;
}
