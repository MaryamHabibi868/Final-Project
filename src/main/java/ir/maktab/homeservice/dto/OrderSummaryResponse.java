package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import lombok.*;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSummaryResponse {

    private Long orderId;

    private ZonedDateTime createDate;

    private OrderStatus status;

    private String homeServiceTitle;

    private String customerName;
}
