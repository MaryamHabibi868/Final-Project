package ir.maktab.homeservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequestDto {

    private Long offerId;
    private String cardNumber;
    private String expiryDate;
    private String cvv2;
    private String secondPassword;
    private String captchaToken;
    private String captchaAnswer;
}
