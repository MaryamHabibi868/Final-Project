package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HomeServiceFound {

    @NotNull
    private Long id;

    @NotBlank
    private String homeServiceTitle;
}
