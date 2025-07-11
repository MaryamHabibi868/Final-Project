package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import ir.maktab.homeservice.domains.enumClasses.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity<Long> {

    public static final String TABLE_NAME = "transaction";
    public static final String AMOUNT = "amount";
    public static final String TRANSACTION_TYPE = "transaction_type";
    public static final String TRANSACTION_DATE = "transaction_date";
    public static final String WALLET_ID = "wallet_id";

    @Column(name = AMOUNT, nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = TRANSACTION_TYPE, nullable = false)
    private TransactionType transactionType;

    @Column(name = TRANSACTION_DATE, nullable = false)
    private ZonedDateTime transactionDate;

    @JoinColumn(name = Transaction.WALLET_ID)
    @ManyToOne
    private Wallet wallet;
}
