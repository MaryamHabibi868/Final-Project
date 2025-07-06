package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;



@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistUpdateInfo {

    @NotNull
    private Long id;

    @NotBlank(groups = {ValidationGroup.Save.class},
            message = "Email should be entered.")
    @Email
    private String email;

    @NotBlank(groups = {ValidationGroup.Save.class},
            message = "Password should be entered.")
    @Size(min = 8, max = 20, message = "Password should be between 8-20 character ")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password should be combination of letters and numbers"
    )
    private String password;

}
