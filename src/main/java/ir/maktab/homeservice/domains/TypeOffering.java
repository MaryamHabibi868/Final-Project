package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class TypeOffering extends BaseEntity {

    public static final String TABLE_NAME = "offering";
    public static final String TYPE_OFFERING_NAME = "type_offering_name";

    @Column(name = Offering.TYPE_OFFERING_NAME, nullable = false, unique = true)
    private String typeOfferingName;
}
