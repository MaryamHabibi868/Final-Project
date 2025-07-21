package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import lombok.*;

import java.time.ZonedDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderFilterRequestForManager {

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private OrderStatus status;

    private Long homeServiceId;

    private Long customerId;

    private Long specialistId;
}
