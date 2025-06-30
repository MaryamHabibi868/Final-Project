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
@ToString (callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
public class Person extends BaseEntity {

    public static final String TABLE_NAME = "person";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @Column(name = Person.FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = Person.LAST_NAME, nullable = false)
    private String lastName;

    @Column(name = Person.EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = Person.PASSWORD, nullable = false, unique = true)
    private String password;
}
