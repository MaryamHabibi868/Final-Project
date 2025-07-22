package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = Role.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Role extends BaseEntity<Long> {

    public static final String TABLE_NAME = "role";
    public static final String NAME = "name";
    public static final String IS_DEFAULT = "is_default";

    @Column(name = NAME)
    private String name;

    @Column(name = IS_DEFAULT)
    private Boolean isDefault;

/*
    @ManyToMany
    private Set<Authority> authorities = new HashSet<>();
*/
}
