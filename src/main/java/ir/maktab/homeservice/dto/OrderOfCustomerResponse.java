package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderOfCustomerResponse {

    @NotNull
    private Long id;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal suggestedPrice;

    @NotNull
    private ZonedDateTime startDate;

    private OrderStatus orderStatus;

    @NotBlank
    private Long addressId;
}
