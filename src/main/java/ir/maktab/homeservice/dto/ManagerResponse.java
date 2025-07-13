package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ManagerResponse {


    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long walletId;
}
