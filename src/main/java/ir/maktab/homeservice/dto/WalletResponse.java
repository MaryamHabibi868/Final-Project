package ir.maktab.homeservice.dto;

import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {

    private Long id;

    private BigDecimal balance;

    private Long userId;
}
