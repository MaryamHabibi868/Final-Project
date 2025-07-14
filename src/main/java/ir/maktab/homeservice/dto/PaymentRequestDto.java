package ir.maktab.homeservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequestDto {

    private Long offerId;           // شناسه پیشنهاد (یا سفارش)
    private String cardNumber;      // شماره کارت (برای ثبت یا ارسال به بانک فرضی)
    private String expiryDate;      // تاریخ انقضا
    private String cvv2;            // cvv2
    private String secondPassword;  // رمز دوم
    private String captchaToken;    // متن کپچا که از بک‌اند گرفتی (در فرانت ذخیره کردی)
    private String captchaAnswer;
}
