package ir.maktab.homeservice.domains;
import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity<Long> {

    public static final String TABLE_NAME = "addresses";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String POSTAL_CODE = "postal_code";
    public static final String DESCRIPTION = "description";
    public static final String CUSTOMER_ID = "customer_id";



    @Column(name = Address.PROVINCE, nullable = false)
    private String province;

    @Column(name = Address.CITY, nullable = false)
    private String city;

    @Column(name = Address.POSTAL_CODE, nullable = false, unique = true)
    private String postalCode;

    @Column(name = Address.DESCRIPTION)
    private String description;

    @JoinColumn(name = Address.CUSTOMER_ID, nullable = false)
    @ManyToOne
    private Customer customer;
}
