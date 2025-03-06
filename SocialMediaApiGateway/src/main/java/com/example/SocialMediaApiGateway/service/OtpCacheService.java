package com.example.SocialMediaApiGateway.service;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;


import java.util.concurrent.TimeUnit;

@Service
public class OtpCacheService {

    private final Cache<String, String> otpCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    public void storeOtp(String email, String otp) {
        otpCache.put(email, otp);
    }

    public String getOtp(String email) {
        return otpCache.getIfPresent(email);
    }

    public void removeOtp(String email) {
        otpCache.invalidate(email);
    }
}

