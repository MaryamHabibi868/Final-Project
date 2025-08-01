package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long walletId;

    private AccountStatus status;

    private Double score;
}
