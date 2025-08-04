package ir.maktab.homeservice.domains.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity<ID extends Serializable> implements Serializable {

    public static final String ID = "id";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private ID id;


    @TimeZoneStorage(TimeZoneStorageType.COLUMN)
    @Column(name = CREATE_DATE, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createDate;


    @TimeZoneStorage(TimeZoneStorageType.COLUMN)
    @Column(name = LAST_UPDATE_DATE, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime lastUpdateDate;


    @PrePersist
    protected void onCreate() {
        createDate = lastUpdateDate =
                ZonedDateTime.now(ZoneId.of("Asia/Tehran"));
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdateDate =
                ZonedDateTime.now(ZoneId.of("Asia/Tehran"));
    }
}
