package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {

    private Long id;

    private BigDecimal balance = BigDecimal.ZERO;


    private Long userId;
}
