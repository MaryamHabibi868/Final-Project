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
public class OrderResponse {


    private Long id;

    private String description;

    private BigDecimal suggestedPrice;

    private ZonedDateTime startDate;

    private OrderStatus status;

    private Long addressId;

    private Long homeServiceId;

    private Long customerId;
}
