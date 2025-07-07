package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.*;
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
public class OrderOfCustomerRequest {

    @NotBlank(message = "Description of Order should be entered.")
    private String description;

    @NotNull(message = "Suggested Price of Order should be entered.")
    private BigDecimal suggestedPrice;

    @NotBlank (message = "Start Date of Order should be entered.")
    private ZonedDateTime startDate;

    @NotBlank (message = "Address of Order should be entered.")
    private Address address;
}
