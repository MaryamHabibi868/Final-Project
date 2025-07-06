package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.AccountStatus;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistSaveUpdateRequest {

    @NotNull(groups = {ValidationGroup.Update.class})
    @Null(groups = {ValidationGroup.Save.class})
    private Long id;

    @NotBlank(groups = {ValidationGroup.Save.class},
            message = "First name should be entered.")
    private String firstName;

    @NotBlank(groups = {ValidationGroup.Save.class},
            message = "Last name should be entered.")
    private String lastName;

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

    @NotBlank(groups = {ValidationGroup.Save.class, ValidationGroup.Update.class})
    private AccountStatus accountStatus;


    @OneToMany
    private Set<Long> homeServicesId;
}
