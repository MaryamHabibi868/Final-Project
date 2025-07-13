package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HomeServiceSaveRequest {

    @NotBlank(message = "The name of main service should be entered.")
    private String title;

    @NotNull( message = "The base price should be entered.")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "The base price must be greater than 0.")
    private BigDecimal basePrice;

    @NotBlank( message = "The Description of home service should be entered.")
    private String description;

    private Long parentServiceId;
}
