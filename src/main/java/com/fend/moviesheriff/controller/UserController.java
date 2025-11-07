package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.domain.dto.userDTOs.CreateUserDTO;
import com.fend.moviesheriff.domain.dto.userDTOs.UserResponseDTO;
import com.fend.moviesheriff.domain.model.User;
import com.fend.moviesheriff.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("profile/{id}")
    public ResponseEntity<UserResponseDTO> findByIdOrThrowException(@PathVariable Long id) {
        return ResponseEntity.ok(userService.mappingExternalMovieByIdToUser(id));
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        return new ResponseEntity<>(userService.saveUser(createUserDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("profile/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("profile/update")
    public ResponseEntity<Void> updateUser(@RequestBody CreateUserDTO createUserDTO) {
        userService.updateUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
