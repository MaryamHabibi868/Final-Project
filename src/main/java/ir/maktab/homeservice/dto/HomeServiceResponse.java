package ir.maktab.homeservice.dto;

import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HomeServiceResponse {


    private Long id;

    private String title;

    private BigDecimal basePrice;

    private String description;

    private Long parentServiceId;
}
