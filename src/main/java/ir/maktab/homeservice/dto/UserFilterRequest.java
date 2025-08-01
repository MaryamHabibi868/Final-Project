package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterRequest {

    private Role role;
    private String firstName;
    private String lastName;
    private String serviceName;
    private Double minScore;
    private Double maxScore;
}
