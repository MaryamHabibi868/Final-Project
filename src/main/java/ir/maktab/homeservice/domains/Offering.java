package ir.maktab.homeservice.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Offering extends TypeOffering {

    public static final String OFFERING_NAME = "offering_name";
    public static final String BASE_PRICE = "base_price";
    public static final String DESCRIPTION = "description";

    @Column(name = Offering.OFFERING_NAME, nullable = false)
    private String offeringName;

    @Column(name = Offering.BASE_PRICE, nullable = false)
    private Double basePrice;

    @Column(name = Offering.DESCRIPTION, nullable = false)
    private String description;

}
