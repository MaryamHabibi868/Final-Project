package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OfferOfSpecialist extends BaseEntity<Long> {

    public static final String TABLE_NAME = "offer_of_specialist";
    public static final String SUGGESTED_PRICE = "suggested_price";
    public static final String START_DATE_SUGGESTION = "start_date_suggestion";
    public static final String TASK_DURATION = "task_duration";
    public final static String SPECIALIST_ID = "specialist_id";
    public final static String ORDER_OF_CUSTOMER_ID = "order_of_customer_id";

    @Column(name = OfferOfSpecialist.SUGGESTED_PRICE, nullable = false)
    private BigDecimal suggestedPrice;

    @Column(name = OfferOfSpecialist.START_DATE_SUGGESTION, nullable = false)
    private ZonedDateTime startDateSuggestion;

    @Column(name = OfferOfSpecialist.TASK_DURATION, nullable = false)
    //Duration
    private Double taskDuration;

    @JoinColumn(name = OfferOfSpecialist.SPECIALIST_ID, nullable = false)
    @ManyToOne
    private Specialist specialist;

    @JoinColumn(name = OfferOfSpecialist.ORDER_OF_CUSTOMER_ID, nullable = false)
    @ManyToOne
    private OrderOfCustomer orderOfCustomer;
}
