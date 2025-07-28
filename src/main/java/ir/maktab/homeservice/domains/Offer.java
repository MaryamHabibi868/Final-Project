package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import ir.maktab.homeservice.domains.enumClasses.OfferStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Offer extends BaseEntity<Long> {

    public static final String TABLE_NAME = "offers";
    public static final String SUGGESTED_PRICE = "suggested_price";
    public static final String START_DATE_SUGGESTION = "start_date_suggestion";
    public static final String TASK_DURATION = "task_duration";
    public final static String SPECIALIST_ID = "specialist_id";
    public final static String ORDER_ID = "order_id";
    public final static String STATUS = "status";

    @Column(name = Offer.SUGGESTED_PRICE, nullable = false)
    private BigDecimal suggestedPrice;

    @Column(name = Offer.START_DATE_SUGGESTION, nullable = false)
    private ZonedDateTime startDateSuggestion;

    @Column(name = Offer.TASK_DURATION, nullable = false)
    private Duration taskDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = Offer.STATUS, nullable = false)
    private OfferStatus status;

    @JoinColumn(name = Offer.SPECIALIST_ID, nullable = false)
    @ManyToOne
    private Specialist specialist;

    @JoinColumn(name = Offer.ORDER_ID, nullable = false)
    @ManyToOne
    private Order orderInformation;


}
