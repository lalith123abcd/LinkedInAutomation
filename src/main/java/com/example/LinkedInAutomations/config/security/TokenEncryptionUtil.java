package com.example.LinkedInAutomations.config.security;

import java.util.Base64;

public class TokenEncryptionUtil {

    private static final String SECRET_KEY = "my-secret-key"; // move to env in real prod

    public static String encrypt(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes());
    }

    public static String decrypt(String encrypted) {
        return new String(Base64.getDecoder().decode(encrypted));
    }
}