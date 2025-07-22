package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    public static final String IS_ACTIVE = "is_active";
    public static final String IS_EMAIL_VERIFY = "is_email_verify";
    public static final String ROLE = "role";


    @Column(name = User.FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = User.LAST_NAME, nullable = false)
    private String lastName;

    @Column(name = User.EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = User.PASSWORD, nullable = false, unique = true)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = User.WALLET_ID, nullable = false)
    private Wallet wallet;

    @Column(name = User.IS_ACTIVE
            /*, nullable = false*/)
    private Boolean isActive = false;

    @Column(name = User.IS_EMAIL_VERIFY
          /*  , nullable = false*/)
    private Boolean isEmailVerify = false;


    @JoinColumn(name = User.ROLE
            /*, nullable = false*/)
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
}
