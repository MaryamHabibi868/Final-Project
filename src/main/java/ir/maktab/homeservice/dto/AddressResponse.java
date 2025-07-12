package ir.maktab.homeservice.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private Long id;

    private String province;

    private String city;

    private String postalCode;

    private String description;

    private Long customerId;
}
