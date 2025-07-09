package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginRequest {

    @NotBlank(message = "Email should be entered for login")
    private String email;

    @NotBlank(message = "Password should be entered for login")
    private String password;
}
