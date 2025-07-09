package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {

    @NotBlank(message = "First name should be entered.")
    private String firstName;

    @NotBlank( message = "Last name should be entered.")
    private String lastName;

    @NotBlank(message = "Email should be entered.")
    @Email
    private String email;

    @NotBlank(message = "Password should be entered.")
    @Size(min = 8, max = 20, message = "Password should be between 8-20 character ")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password should be combination of letters and numbers"
    )
    private String password;

}
