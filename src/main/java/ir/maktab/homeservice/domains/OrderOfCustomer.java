package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
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
public class OrderOfCustomer extends BaseEntity<Long> {

    public static final String TABLE_NAME = "order_of_customer";
    public static final String DESCRIPTION = "description";
    public static final String SUGGESTED_PRICE = "suggested_price";
    public static final String START_DATE = "start_date";
    public static final String ADDRESS_ID = "address_id";
    public static final String ORDER_STATUS = "order_status";
    public static final String HOME_SERVICE_ID = "home_service_id";
    public static final String CUSTOMER_ID = "customer_id";

    @Column(name = OrderOfCustomer.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = OrderOfCustomer.SUGGESTED_PRICE, nullable = false)
    private BigDecimal suggestedPrice;

    @Column(name = OrderOfCustomer.START_DATE, nullable = false)
    private ZonedDateTime startDate;

    @JoinColumn(name = OrderOfCustomer.ADDRESS_ID, nullable = false)
    @ManyToOne
    private Address address;

    @Column(name = OrderOfCustomer.ORDER_STATUS, nullable = false)
    private OrderStatus orderStatus;

    @JoinColumn(name = OrderOfCustomer.HOME_SERVICE_ID, nullable = false)
    @ManyToOne
    private HomeService homeService;

    /*@OneToMany(mappedBy = "orderOfCustomer")
    private Set<OfferOfSpecialist> offerOfSpecialists;*/

    @JoinColumn(name = OrderOfCustomer.CUSTOMER_ID, nullable = false)
    @ManyToOne
    private Customer customer;
}
