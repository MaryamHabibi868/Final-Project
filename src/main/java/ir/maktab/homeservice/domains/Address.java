package ir.maktab.homeservice.domains;
import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity<Long> {

    public static final String TABLE_NAME = "address";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String STREET = "street";
    public static final String ALLEY = "alley";
    public static final String POSTAL_CODE = "postal_code";
    public static final String PLAQUE = "plaque";
    public static final String UNIT = "unit";
    public static final String DESCRIPTION = "description";



    @Column(name = Address.PROVINCE, nullable = false)
    private String province;

    @Column(name = Address.CITY, nullable = false)
    private String city;

    @Column(name = Address.STREET, nullable = false)
    private String street;

    @Column(name = Address.ALLEY, nullable = false)
    private String alley;

    @Column(name = Address.POSTAL_CODE, nullable = false)
    private String postalCode;

    @Column(name = Address.PLAQUE, nullable = false)
    private String plaque;

    @Column(name = Address.UNIT, nullable = false)
    private String unit;

    @Column(name = Address.DESCRIPTION)
    private String description;

    @OneToMany (mappedBy = "address")
    private Set<Offer> offers;
}
