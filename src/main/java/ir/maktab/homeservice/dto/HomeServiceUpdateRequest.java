package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeServiceUpdateRequest {

    @NotNull
    private Long id;

    private String title;

    private BigDecimal basePrice;

    private String description;

    private Long parentServiceId;
}
