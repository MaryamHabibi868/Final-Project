package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.Address;
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

    @NotBlank
    private ZonedDateTime startDate;

    @NotBlank
    private Address address;
}
