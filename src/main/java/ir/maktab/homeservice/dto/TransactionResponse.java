package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.TransactionType;
import lombok.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;

    private BigDecimal amount;

    private TransactionType type;

    private ZonedDateTime date;

    private Long walletId;
}
