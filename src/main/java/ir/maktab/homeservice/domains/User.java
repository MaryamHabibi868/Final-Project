package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.naming.Name;

@Entity
@Getter
@Setter
@Table (name = User.TABLE_NAME)
@ToString (callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
public class User extends BaseEntity<Long> {

    public static final String TABLE_NAME = "users";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String WALLET_ID = "wallet_id";

    @Column(name = User.FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = User.LAST_NAME, nullable = false)
    private String lastName;

    @Column(name = User.EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = User.PASSWORD, nullable = false, unique = true)
    private String password;

    @JoinColumn(name = User.WALLET_ID, nullable = false)
    @OneToOne
    private Wallet wallet;
}
