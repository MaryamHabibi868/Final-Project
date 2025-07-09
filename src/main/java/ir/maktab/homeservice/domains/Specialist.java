package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = "Specialist")
public class Specialist extends User {

    public static final String ACCOUNT_STATUS = "account_status";
    public static final String REGISTRATION_DATE = "registration_date";
    public static final String BALANCE = "balance";

    @Column(name = Specialist.ACCOUNT_STATUS, nullable = false)
    private AccountStatus accountStatus;

    @Column(name = Specialist.BALANCE)
    private BigDecimal balance;


    @ManyToMany
    private Set<HomeService> homeServices;

    public BigDecimal balance() {
        balance = BigDecimal.valueOf(0.0);
        return balance;
    }
}


