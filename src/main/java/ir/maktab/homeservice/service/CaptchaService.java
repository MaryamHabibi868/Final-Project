package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.CaptchaDto;

public interface CaptchaService {

    CaptchaDto generate();            // ایجاد کپچا جدید


    boolean verify(String token, String userAnswer);   // اعتبارسنجی پاسخ کاربر
}
