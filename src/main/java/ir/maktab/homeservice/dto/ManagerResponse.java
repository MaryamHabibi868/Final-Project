package ir.maktab.homeservice.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerResponse {


    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long walletId;
}
