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


    @Column(name = Specialist.ACCOUNT_STATUS)
    private AccountStatus accountStatus;

    @JoinTable(name = "specialist_homeService")
    @ManyToMany
    private Set<HomeService> homeServices;

}


