package com.fend.moviesheriff.domain.service.auth.utils;

import jakarta.servlet.http.HttpServletRequest;

public class TokenRecoveryUtil {
    public static String recoveryToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) return authHeader
                .replace("Bearer ", "");

        return null;
    }
}
