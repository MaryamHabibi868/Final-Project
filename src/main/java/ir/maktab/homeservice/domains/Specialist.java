package ir.maktab.homeservice.domains;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = "Specialist")
public class Specialist extends Person {

    public static final String ACCOUNT_STATUS = "account_status";
    public static final String REGISTRATION_DATE = "registration_date";

    @Column(name = Specialist.ACCOUNT_STATUS, nullable = false)
    private AccountStatus accountStatus;

    @Column(name = Specialist.REGISTRATION_DATE)
    private LocalDateTime registrationDate;
}

