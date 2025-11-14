package com.fend.moviesheriff.domain.service.auth;

import com.fend.moviesheriff.domain.service.auth.token.JwtTokenService;
import com.fend.moviesheriff.domain.service.auth.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public String login(String username, String password) {
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(username, password);
        var authentication = authenticationManager.authenticate(usernamePasswordToken);

        var userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtTokenService.generateToken(userDetails);
    }
}
