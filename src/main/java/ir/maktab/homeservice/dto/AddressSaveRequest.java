package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressSaveRequest {

    @NotBlank (message = "The province should be entered.")
    private String province;

    @NotBlank (message = "The city should be entered.")
    private String city;

    @NotBlank (message = "The postal code should be entered.")
    @Size(min = 10, max = 10 , message = "The length of postal code is 10.")
    private String postalCode;


    private String description;

    @NotNull (message = "The customer id should be entered.")
    private Long customerId;
}
