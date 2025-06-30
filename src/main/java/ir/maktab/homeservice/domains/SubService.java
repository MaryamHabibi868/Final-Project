package ir.maktab.homeservice.domains;

import com.fasterxml.jackson.databind.ser.Serializers;
import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubService extends BaseEntity {

    public static final String TABLE_NAME = "sub_service";
    public static final String SUB_SERVICE_TITLE = "sub_service_title";
    public static final String BASE_PRICE = "base_price";
    public static final String DESCRIPTION = "description";

    @Column(name = SubService.SUB_SERVICE_TITLE, nullable = false)
    private String subServiceTitle;

    @Column(name = SubService.BASE_PRICE, nullable = false)
    private Double basePrice;

    @Column(name = SubService.DESCRIPTION, nullable = false)
    private String description;


}
