package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class HomeService extends BaseEntity<Long> {

    public static final String TABLE_NAME = "home_service";
    public static final String HOME_SERVICE_TITLE = "home_service_title";
    public static final String BASE_PRICE = "base_price";
    public static final String DESCRIPTION = "description";
    public static final String PARENT_SERVICE = "parent_service";


    @Column(name = HomeService.HOME_SERVICE_TITLE, nullable = false, unique = true)
    private String homeServiceTitle;

    @Column(name = HomeService.BASE_PRICE, nullable = false)
    private BigDecimal basePrice;

    @Column(name = HomeService.DESCRIPTION, nullable = false)
    private String description;

    @JoinColumn(name = HomeService.PARENT_SERVICE)
    @ManyToOne
    private HomeService parentService;
}
