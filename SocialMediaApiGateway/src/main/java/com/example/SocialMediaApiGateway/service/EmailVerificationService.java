package com.example.SocialMediaApiGateway.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailOtpService emailService;

    public String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void sendVerificationCode(String email) {
        String code = generateVerificationCode();
        emailService.sendEmail(email, "Verification Code", "Please use the following code to complete your login: " + code);
    }

    public boolean verifyCode(String userEmail, String userEnteredCode, String sentCode) {
        return sentCode.equals(userEnteredCode);
    }
}
