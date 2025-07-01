package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MainServiceSaveUpdateRequest {

    @NotNull(groups = {ValidationGroup.Update.class})
    @Null(groups = {ValidationGroup.Save.class})
    private Long id;

    @NotBlank(groups = {ValidationGroup.Save.class},
            message = "The name of main service should be entered.")
    private String mainServiceTitle;
}
