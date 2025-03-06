package com.example.SocialMediaApiGateway;

import com.example.SocialMediaApiGateway.config.JwtUtil;
import com.example.SocialMediaApiGateway.filter.AuthenticationFilter;
import com.example.SocialMediaApiGateway.service.EmailOtpService;
import com.example.SocialMediaApiGateway.service.OtpCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;



    @Mock
    private OtpCacheService otpCacheService;



    @Autowired
    private MockMvc mockMvc;  // Autowired MockMvc

    private String validJwtToken = "valid-jwt-token";
    private String invalidJwtToken = "invalid-jwt-token";
    private String userEmail = "test@example.com";
    private String otp = "123456";

    @BeforeEach
    void setUp() {
        when(jwtUtil.extractUserName(validJwtToken)).thenReturn(userEmail);
        when(otpCacheService.getOtp(userEmail)).thenReturn(otp);
    }

    @Test
    void testSuccessfulOtpValidation() throws Exception {
        mockMvc.perform(get("/posts/LikeAndComment/2")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .header("OTP", otp))
                .andExpect(status().isOk());
    }

    @Test
    void testInvalidOtpValidation() throws Exception {
        mockMvc.perform(get("/posts/LikeAndComment/2")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .header("OTP", "wrong-otp"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid OTP"));
    }

    @Test
    void testMissingOtp() throws Exception {
        mockMvc.perform(get("/posts/LikeAndComment/2")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("OTP required. Please check your email."));
    }

    @Test
    void testInvalidJwtToken() throws Exception {
        mockMvc.perform(get("/posts/LikeAndComment/2")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidJwtToken)
                        .header("OTP", otp))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid Token"));
    }
}
