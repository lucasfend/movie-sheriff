package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.domain.dto.userDTOs.CreateUserDTO;
import com.fend.moviesheriff.domain.dto.userDTOs.UserLoginDTO;
import com.fend.moviesheriff.domain.service.auth.AuthenticationService;
import com.fend.moviesheriff.domain.service.persistence.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("login")
    @SecurityRequirements()
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authenticationService.login(userLoginDTO.username(), userLoginDTO.password());
        return ResponseEntity.ok(token);
    }

    @PostMapping("register")
    @SecurityRequirements()
    public ResponseEntity<Void> register(@RequestBody CreateUserDTO createUserDTO) {
        userService.saveUser(createUserDTO);
        return  ResponseEntity.ok().build();
    }
}
