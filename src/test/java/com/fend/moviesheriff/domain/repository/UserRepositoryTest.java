package com.fend.moviesheriff.domain.repository;

import com.fend.moviesheriff.domain.dto.CreateUserDTO;
import com.fend.moviesheriff.domain.mapper.UserMapper;
import com.fend.moviesheriff.domain.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Tests for UserRepository")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private CreateUserDTO createUser() {
        return CreateUserDTO.builder()
                .username("nick_cage")
                .email("nick_cage@gmail.com")
                .password("0000")
                .build();
    }

    private User toUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setUsername(createUserDTO.username());
        user.setEmail(createUserDTO.email());
        user.setPassword(createUserDTO.password());

        return user;
    }

    @Test
    @DisplayName("Save method persists user when successful")
    void saveUser_PersistUserOnDatabase_WhenSuccessful() {
        CreateUserDTO userToSave = createUser();

        User savedUser = this.userRepository.save(toUser(userToSave));

        Assertions.assertThat(savedUser).isNotNull();

        Assertions.assertThat(savedUser.getId()).isNotNull();

        Assertions.assertThat(savedUser.getUsername()).isNotNull();
        Assertions.assertThat(savedUser.getEmail()).isNotNull();
        Assertions.assertThat(savedUser.getPassword()).isNotNull();

        Assertions.assertThat(savedUser.getUsername()).isEqualTo(userToSave.username());
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(userToSave.email());
        Assertions.assertThat(savedUser.getPassword()).isEqualTo(userToSave.password());
    }

    @Test
    @DisplayName("Save method persists user when successful")
    void saveUser_UpdateUserOnDatabase_WhenSuccessful() {
        CreateUserDTO userToSave = createUser();
        User savedUser = this.userRepository.save(toUser(userToSave));

        savedUser.setUsername("updated username");
        User updatedUser = this.userRepository.save(savedUser);

        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();

        Assertions.assertThat(updatedUser.getUsername()).isEqualTo(savedUser.getUsername());
    }
}