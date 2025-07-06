package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OfferOfCustomer extends BaseEntity<Long> {

    public static final String TABLE_NAME = "offer_of_customer";
    public static final String DESCRIPTION = "description";
    public static final String SUGGESTED_PRICE = "suggested_price";
    public static final String START_DATE = "start_date";
    public static final String ADDRESS = "address";
    public static final String OFFER_STATUS = "offer_status";

    @Column(name = OfferOfCustomer.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = OfferOfCustomer.SUGGESTED_PRICE, nullable = false)
    private BigDecimal suggestedPrice;

    @Column(name = OfferOfCustomer.START_DATE, nullable = false)
    private ZonedDateTime startDate;

    @JoinColumn(name = OfferOfCustomer.ADDRESS, nullable = false)
    @ManyToOne
    private Address address;

    @Column(name = OfferOfCustomer.OFFER_STATUS, nullable = false)
    private OfferStatus offerStatus;

    public void startDate() {
        startDate = ZonedDateTime.now();
    }
}
