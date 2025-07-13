package ir.maktab.homeservice.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {


    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long walletId;
}
