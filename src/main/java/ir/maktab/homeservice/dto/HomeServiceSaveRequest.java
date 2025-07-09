package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.HomeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HomeServiceSaveRequest {

    @NotBlank(message = "The name of main service should be entered.")
    private String homeServiceTitle;

    @NotBlank( message = "The name of main service should be entered.")
    private BigDecimal basePrice;

    @NotBlank( message = "The name of main service should be entered.")
    private String description;

    private HomeService parentService;
}
