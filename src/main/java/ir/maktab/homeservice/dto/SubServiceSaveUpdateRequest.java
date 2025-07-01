package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.domains.MainService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubServiceSaveUpdateRequest {

    @NotNull(groups = {ValidationGroup.Update.class})
    private Long id;

    @NotNull(groups = {ValidationGroup.Save.class},
            message = "The name of sub service should be entered.")
    private String subServiceTitle;

    @NotNull(groups = {ValidationGroup.Save.class},
            message = "The price of service should be entered.")
    private Double basePrice;

    @NotNull(groups = {ValidationGroup.Save.class},
            message = "The description of service should be entered.")
    @Size(groups = {ValidationGroup.Save.class}, min = 20, max = 1000,
            message = "The length of description should be between 20 and 1000 character ")
    private String description;

    @NotNull(groups = {ValidationGroup.Save.class, ValidationGroup.Update.class},
    message = "The main service should be entered.")
    private MainService mainService;
}
