package ir.maktab.homeservice.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaDto {

    private String text;
    private String imageBase64;

}
