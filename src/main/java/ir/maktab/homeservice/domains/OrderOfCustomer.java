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
public class OrderOfCustomer extends BaseEntity<Long> {

    public static final String TABLE_NAME = "order_of_customer";
    public static final String DESCRIPTION = "description";
    public static final String SUGGESTED_PRICE = "suggested_price";
    public static final String START_DATE = "start_date";
    public static final String ADDRESS = "address";
    public static final String OFFER_STATUS = "offer_status";

    @Column(name = OrderOfCustomer.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = OrderOfCustomer.SUGGESTED_PRICE, nullable = false)
    private BigDecimal suggestedPrice;

    @Column(name = OrderOfCustomer.START_DATE, nullable = false)
    private ZonedDateTime startDate;

    @JoinColumn(name = OrderOfCustomer.ADDRESS, nullable = false)
    @ManyToOne
    private Address address;

    @Column(name = OrderOfCustomer.OFFER_STATUS, nullable = false)
    private OfferStatus offerStatus;

    @ManyToMany
    private Set<OfferOfSpecialist> offerOfSpecialists;

    public void startDate() {
        startDate = ZonedDateTime.now();
    }
}
