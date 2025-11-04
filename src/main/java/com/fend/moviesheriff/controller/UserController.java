package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.domain.dto.CreateUserDTO;
import com.fend.moviesheriff.domain.dto.UserResponseDTO;
import com.fend.moviesheriff.domain.mapper.UserMapper;
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
    private final UserMapper userMapper;

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> findByIdOrThrowException(@PathVariable Long id) {
        return ResponseEntity.ok(userService.mappingExternalMovieByIdToUser(id));
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        return new ResponseEntity<>(userService.saveUser(createUserDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody CreateUserDTO createUserDTO) {
        return new ResponseEntity<>(userService.updateUser(createUserDTO), HttpStatus.OK);
    }
}
