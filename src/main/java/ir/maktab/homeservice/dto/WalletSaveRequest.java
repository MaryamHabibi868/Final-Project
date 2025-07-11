package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletSaveRequest {

    @NotNull
    private BigDecimal balance = BigDecimal.ZERO;

    @NotNull
    private Long userId;
}
