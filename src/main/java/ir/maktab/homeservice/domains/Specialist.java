package ir.maktab.homeservice.domains;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @Column(name = Specialist.ACCOUNT_STATUS, nullable = false)
    private AccountStatus accountStatus;


    @ManyToMany
    private Set<HomeService> homeServices;
}

