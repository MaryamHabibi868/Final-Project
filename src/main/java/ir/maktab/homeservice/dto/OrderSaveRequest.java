package ir.maktab.homeservice.dto;

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
public class OrderSaveRequest {

    @NotBlank(message = "Description of Order should be entered.")
    private String description;

    @NotNull(message = "Suggested Price of Order should be entered.")
    private BigDecimal suggestedPrice;

    @NotNull(message = "Start Date of Order should be entered.")
    private ZonedDateTime startDate;

    @NotNull (message = "Address of Order should be entered.")
    private Long addressId;

    @NotNull
    private Long customerId;

    @NotNull
    private Long homeServiceId;
}
