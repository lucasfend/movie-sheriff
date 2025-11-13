package com.fend.moviesheriff.domain.service.auth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fend.moviesheriff.domain.service.auth.user.UserDetailsImpl;
import com.fend.moviesheriff.domain.service.auth.utils.DateInstanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    @Value("${api.security.token.secret}")
    private String SECRET_KEY;

    @Value("${api.security.token.issuer}")
    private String ISSUER;

    public String generateToken(UserDetailsImpl userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(DateInstanceUtil.creationDate())
                    .withExpiresAt(DateInstanceUtil.expirationDate())
                    .withSubject(userDetails.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error while generating token", e);
        }
    }

    public String getSubjectFromTokenGivenOnRequest(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTCreationException("Invalid or expired token", e);
        }
    }
}
