package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.CaptchaDto;

public interface CaptchaService {


    CaptchaDto generate();


    boolean verify(String token, String userAnswer);
}
