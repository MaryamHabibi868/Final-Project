package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.enumClasses.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddRemoveSToHResponse {

        private Long id;

        private String firstName;

        private String lastName;

        private Double score;

        private String message = "Add or Remove specialist to home service successfully";

    }