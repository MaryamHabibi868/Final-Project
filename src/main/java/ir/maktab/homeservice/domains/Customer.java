package ir.maktab.homeservice.domains;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@DiscriminatorValue(value = "Customer")
public class Customer extends User {

    public static final String BALANCE = "balance";

    @Column(name = Customer.BALANCE)
    private BigDecimal balance;

    public BigDecimal balance() {
        balance = BigDecimal.valueOf(0.0);
        return balance;
    }
}
