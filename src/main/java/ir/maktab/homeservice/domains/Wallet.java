package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet extends BaseEntity<Long> {

     public static final String BALANCE = "balance";
     public static final String USER_ID = "user_id";

    @Column(name = Wallet.BALANCE)
    private BigDecimal balance = BigDecimal.ZERO;

    @JoinColumn(name = Wallet.USER_ID)
    @OneToOne
    private User userInformation;


    /*public BigDecimal balance() {
        balance = BigDecimal.valueOf(0.0);
        return balance;
    }*/


}
