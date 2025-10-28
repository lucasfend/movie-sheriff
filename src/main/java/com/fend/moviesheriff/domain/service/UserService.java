package com.fend.moviesheriff.domain.service;

import com.fend.moviesheriff.dto.UserDTO;
import com.fend.moviesheriff.domain.mapper.UserMapper;
import com.fend.moviesheriff.domain.model.User;
import com.fend.moviesheriff.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User findByIdOrThrowException(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    public void deleteUserById(Long id) {
        userRepository.delete(findByIdOrThrowException(id));
    }

    public User saveUser(UserDTO userDTO) {
        return userRepository.save(userMapper.toUser(userDTO));
    }

    public User updateUser(UserDTO userDTO) {
        User savedUser = findByIdOrThrowException(userMapper.toUser(userDTO).getId());
        User user = userMapper.toUser(userDTO);
        user.setId(savedUser.getId());

        return userRepository.save(user);
    }
}
