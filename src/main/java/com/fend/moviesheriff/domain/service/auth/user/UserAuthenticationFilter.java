package com.fend.moviesheriff.domain.service.auth.user;

import com.fend.moviesheriff.domain.model.User;
import com.fend.moviesheriff.domain.repository.UserRepository;
import com.fend.moviesheriff.domain.service.auth.token.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.fend.moviesheriff.domain.service.auth.utils.CheckEndpointUtil.checkIfEndpointIsNotPublic;
import static com.fend.moviesheriff.domain.service.auth.utils.TokenRecoveryUtil.recoveryToken;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);
            if (token != null) {
                String subjectRecover = jwtTokenService.getSubjectFromTokenGivenOnRequest(token);
                User searchUser = userRepository.findByUsername(subjectRecover).get();
                UserDetailsImpl userDetails = new UserDetailsImpl(searchUser);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new BadCredentialsException("Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
