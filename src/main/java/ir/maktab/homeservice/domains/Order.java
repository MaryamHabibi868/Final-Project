package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = Order.TABLE_NAME)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity<Long> {

    public static final String TABLE_NAME = "orders";
    public static final String DESCRIPTION = "description";
    public static final String SUGGESTED_PRICE = "suggested_price";
    public static final String START_DATE = "start_date";
    public static final String ADDRESS_ID = "address_id";
    public static final String STATUS = "status";
    public static final String HOME_SERVICE_ID = "home_service_id";
    public static final String CUSTOMER_ID = "customer_id";

    @Column(name = Order.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = Order.SUGGESTED_PRICE, nullable = false)
    private BigDecimal suggestedPrice;

    @Column(name = Order.START_DATE, nullable = false)
    private ZonedDateTime startDate;

    @JoinColumn(name = Order.ADDRESS_ID, nullable = false)
    @ManyToOne
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = Order.STATUS, nullable = false)
    private OrderStatus status;

    @JoinColumn(name = Order.HOME_SERVICE_ID, nullable = false)
    @ManyToOne
    private HomeService homeService;

    @JoinColumn(name = Order.CUSTOMER_ID, nullable = false)
    @ManyToOne
    private Customer customer;

    @OneToMany (mappedBy = "orderInformation")
    private Set<Offer> offers = new HashSet<>();
}
