package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MainService extends BaseEntity<Long> {

    public static final String TABLE_NAME = "main_service";
    public static final String MAIN_SERVICE_TITLE = "main_service_title";

    @Column(name = MainService.MAIN_SERVICE_TITLE, nullable = false, unique = true)
    private String mainServiceTitle;

    @OneToMany (mappedBy = "mainService")
    private Set<SubService> subServices = new HashSet<>();
}
