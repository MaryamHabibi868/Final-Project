package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseForManager {

    private Long orderId;

    private ZonedDateTime createDate;

    private String description;

    private BigDecimal suggestedPrice;

    private ZonedDateTime startDate;

    private OrderStatus status;

    private AddressResponse address;

    private HomeServiceResponse homeService;

    private CustomerResponse customer;
}
