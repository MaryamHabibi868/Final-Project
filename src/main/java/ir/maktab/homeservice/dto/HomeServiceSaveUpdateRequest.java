package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.HomeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HomeServiceSaveUpdateRequest {

    @NotNull(groups = {ValidationGroup.Update.class})
    @Null(groups = {ValidationGroup.Save.class})
    private Long id;

    @NotBlank(groups = {ValidationGroup.Save.class},
            message = "The name of main service should be entered.")
    private String homeServiceTitle;

    @NotBlank(groups = {ValidationGroup.Save.class},
            message = "The name of main service should be entered.")
    private Double basePrice;

    @NotBlank(groups = {ValidationGroup.Save.class},
            message = "The name of main service should be entered.")
    private String description;


    private HomeService parentService;
}
