package com.example.SocialMediaApiGateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailOtpService {

    @Autowired
    private JavaMailSender emailSender;

    public String generateOtp() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(999999));
    }

    public void sendOtp(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");  // Replace with the sender's email
        message.setTo(toEmail);
        message.setSubject("Your OTP for 2FA");
        message.setText("Your OTP is: " + otp + "\nIt will expire in 5 minutes.");

        emailSender.send(message);
    }
}
