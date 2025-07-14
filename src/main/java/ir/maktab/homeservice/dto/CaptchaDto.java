package ir.maktab.homeservice.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaDto {

        private String text;          // متن کپچا که برای اعتبارسنجی ذخیره می‌شود
        private String imageBase64;   // مثلا یک UUID

}
