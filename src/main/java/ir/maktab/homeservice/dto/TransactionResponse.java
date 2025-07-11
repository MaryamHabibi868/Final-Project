package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.domains.enumClasses.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;

    private BigDecimal amount;

    private TransactionType transactionType;

    private ZonedDateTime transactionDate;

    private Long walletId;
}
