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
public class HomeServiceUpdateRequest {

    @NotNull
    private Long id;

    private String homeServiceTitle;

    private BigDecimal basePrice;

    private String description;

    private HomeService parentService;
}
