package ir.maktab.homeservice.dto;

import jakarta.validation.constraints.NotNull;

public class MainServiceSaveUpdateRequest {

    @NotNull(groups = {ValidationGroup.Update.class})
    private Long id;

    @NotNull(groups = {ValidationGroup.Save.class},
            message = "The name of main service should be entered.")
    private String mainServiceTitle;
}
