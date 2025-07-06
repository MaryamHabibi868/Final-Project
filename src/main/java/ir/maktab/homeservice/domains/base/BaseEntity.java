package ir.maktab.homeservice.domains.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity<ID extends Serializable> implements Serializable {

    public static final String ID = "id";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";
    public static final String IS_ACTIVE = "is_active";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private ID id;

    @Column(name = CREATE_DATE)
    @CreatedDate
    private ZonedDateTime createDate;

    @Column(name = LAST_UPDATE_DATE)
    @LastModifiedDate
    private ZonedDateTime lastUpdateDate;

    @Column(name = IS_ACTIVE)
    private Boolean isActive;

    @PrePersist
    public void registrationDate() {
        createDate = ZonedDateTime.now();
        isActive = true;
    }
}
