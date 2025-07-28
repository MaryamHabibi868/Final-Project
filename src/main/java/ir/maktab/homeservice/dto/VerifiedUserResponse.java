package ir.maktab.homeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifiedUserResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String message = "Your profile verified successfully";
}
