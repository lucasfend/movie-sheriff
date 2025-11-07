package com.fend.moviesheriff.domain.repository;

import com.fend.moviesheriff.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
