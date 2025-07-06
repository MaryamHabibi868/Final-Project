package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeService extends BaseEntity<Long> {

    public static final String TABLE_NAME = "home_service";
    public static final String HOME_SERVICE_TITLE = "home_service_title";
    public static final String BASE_PRICE = "base_price";
    public static final String DESCRIPTION = "description";
    public static final String PARENT_SERVICE = "parent_service";


    @Column(name = HomeService.HOME_SERVICE_TITLE, nullable = false, unique = true)
    private String homeServiceTitle;

    @Column(name = HomeService.BASE_PRICE, nullable = false)
    private Double basePrice;

    @Column(name = HomeService.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = HomeService.PARENT_SERVICE)
    @ManyToOne
    private HomeService parentService;
}
