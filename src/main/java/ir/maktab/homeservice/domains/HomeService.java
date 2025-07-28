package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeService extends BaseEntity<Long> {

    public static final String TABLE_NAME = "home_services";
    public static final String TITLE = "title";
    public static final String BASE_PRICE = "base_price";
    public static final String DESCRIPTION = "description";
    public static final String PARENT_SERVICE = "parent_service";


    @Column(name = HomeService.TITLE, nullable = false, unique = true)
    private String title;

    @Column(name = HomeService.BASE_PRICE, nullable = false)
    private BigDecimal basePrice;

    @Column(name = HomeService.DESCRIPTION, nullable = false)
    private String description;

    @JoinColumn(name = HomeService.PARENT_SERVICE)
    @ManyToOne(cascade = CascadeType.PERSIST)
    private HomeService parentService;
}
