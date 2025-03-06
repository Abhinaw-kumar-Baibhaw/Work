package com.example.SocialMediaApiGateway;

import com.example.SocialMediaApiGateway.service.EmailOtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class EmailOtpServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailOtpService emailOtpService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateOtp() {
        String otp = emailOtpService.generateOtp();
        assertNotNull(otp, "Generated OTP should not be null");
        assertEquals(6, otp.length(), "OTP length should be 6");
    }

    @Test
    void testSendEmail() {
        String toEmail = "abhinaw2801@gmail.com";
        String otp = "123456";
        String subject = "Your OTP for 2FA";
        emailOtpService.sendEmail(toEmail, otp, subject);
        verify(javaMailSender, times(1)).send((SimpleMailMessage) any());
    }
}
