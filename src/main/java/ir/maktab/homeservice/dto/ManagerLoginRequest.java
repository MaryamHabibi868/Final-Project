package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerLoginRequest {

    @NotBlank(message = "Email should be entered for login")
    private String email;

    @NotBlank(message = "Password should be entered for login")
    private String password;
}
