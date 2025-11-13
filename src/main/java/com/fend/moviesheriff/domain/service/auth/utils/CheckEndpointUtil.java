package com.fend.moviesheriff.domain.service.auth.utils;

import com.fend.moviesheriff.config.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class CheckEndpointUtil {
    public static boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_NO_AUTH_REQUIRED).contains(requestURI);
    }
}
