package com.example.SocialMediaApiGateway.filter;

import com.example.SocialMediaApiGateway.config.JwtUtil;
import com.example.SocialMediaApiGateway.service.EmailOtpService;
import com.example.SocialMediaApiGateway.service.OtpCacheService;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.logging.Logger;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailOtpService emailOtpService;

    @Autowired
    private OtpCacheService otpCacheService;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.warning("Missing Authorization header in request: " + exchange.getRequest().getURI());
                    return Mono.error(new RuntimeException("Missing Authorization header"));
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                logger.info("Forwarded Authorization Header: " + authHeader);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7); // Extract the token
                } else {
                    logger.warning("Authorization header does not start with 'Bearer '");
                    return Mono.error(new RuntimeException("Authorization header must start with 'Bearer '"));
                }

                try {
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    logger.severe("Invalid Token: " + e.getMessage());
                    return Mono.error(new RuntimeException("Invalid Token"));
                }

                String userEmail = jwtUtil.extractUserName(authHeader);
                String otp = exchange.getRequest().getHeaders().getFirst("OTP");

                if (otp == null) {
                    String generatedOtp = emailOtpService.generateOtp();
                    emailOtpService.sendEmail(userEmail, generatedOtp, "");
                    otpCacheService.storeOtp(userEmail, generatedOtp);
                    return Mono.error(new RuntimeException("OTP required. Please check your email.")); // Notify the user to check email
                }
                String storedOtp = otpCacheService.getOtp(userEmail);
                if (storedOtp == null) {
                    return Mono.error(new RuntimeException("OTP has expired or was never generated. Please request a new OTP."));
                }
                if (!storedOtp.equals(otp)) {
                    return Mono.error(new RuntimeException("Invalid OTP"));
                }
            }
            return chain.filter(exchange);
        };
    }
    public static class Config {
    }
}

