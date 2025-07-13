package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
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

    public static final String STATUS = "status";
    public static final String SCORE = "score";

    @Enumerated(EnumType.STRING)
    @Column(name = Specialist.STATUS)
    private AccountStatus status;

    @Column(name = Specialist.SCORE)
    private Integer score;

    @JoinTable(name = "specialist_homeService")
    @ManyToMany
    private Set<HomeService> homeServices;

}


