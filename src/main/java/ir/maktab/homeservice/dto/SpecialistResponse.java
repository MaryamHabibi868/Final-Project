package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistResponse {


    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long walletId;

    private AccountStatus status;

    private Integer score;

}
