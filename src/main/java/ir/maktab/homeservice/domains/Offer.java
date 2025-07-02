package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Offer extends BaseEntity {

    public static final String TABLE_NAME = "offer";
    public static final String DESCRIPTION = "description";
    public static final String SUGGESTED_PRICE = "suggested_price";
    public static final String OFFER_STATUS = "offer_status";

    @Column(name = Offer.DESCRIPTION)
    private String description;

    @Column(name = Offer.SUGGESTED_PRICE, nullable = false)
    private BigDecimal suggestedPrice;

    private OfferStatus offerStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;
}
