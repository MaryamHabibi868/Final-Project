package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.HomeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HomeServiceResponse {

    @NotNull
    private Long id;

    @NotBlank
    private String homeServiceTitle;

    @NotBlank
    private BigDecimal basePrice;

    @NotBlank
    private String description;

    private HomeService parentService;
}
