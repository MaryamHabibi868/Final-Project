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
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity<Long> {

    public static final String TABLE_NAME = "transactions";
    public static final String AMOUNT = "amount";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String WALLET_ID = "wallet_id";

    @Column(name = AMOUNT, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = TYPE, nullable = false)
    private TransactionType type;

    @Column(name = DATE, nullable = false)
    private ZonedDateTime date;

    @JoinColumn(name = Transaction.WALLET_ID)
    @ManyToOne
    private Wallet wallet;
}
