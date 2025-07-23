package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

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

    @Column(name = Role.NAME)
    private String name;

    @Column(name = Role.IS_DEFAULT)
    private Boolean isDefault;

    @JoinTable(name = "authorities-roles")
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Authority> authorities = new HashSet<>();

}
