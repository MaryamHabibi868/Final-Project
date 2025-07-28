package ir.maktab.homeservice.domains;

import ir.maktab.homeservice.domains.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken extends BaseEntity<Long> {

    public static final String TABLE_NAME = "email_verification_tokens";
    public static final String TOKEN = "token";
    public static final String EXPIRYDATE = "expiry_date";
    public static final String USED = "used";
    public static final String USERID = "user_id";

    @Column(name = VerificationToken.TOKEN)
    private String token;

    @Column(name = VerificationToken.EXPIRYDATE)
    private LocalDateTime expiryDate;

    @Column(name = VerificationToken.USED)
    private boolean used;

    @JoinColumn(name = VerificationToken.USERID)
    @OneToOne
    private User user;
}
