package ir.maktab.homeservice.domains.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean active = true;

    private ZonedDateTime createDate;

   /* @PrePersist
    public void isActive() {
        this.active = true;
    }*/

    @PrePersist
    public void registrationDate() {
        createDate = ZonedDateTime.now();
    }
}
