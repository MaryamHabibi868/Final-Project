package ir.maktab.homeservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequestDto {

    private Long walletId;
    private BigDecimal amount;
    private String cardNumber;
    private String expiryDate;
    private String cvv2;
    private String secondPassword;
    private String captchaToken;
    private String captchaAnswer;
}
