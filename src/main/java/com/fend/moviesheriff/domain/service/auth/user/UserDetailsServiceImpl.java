package com.fend.moviesheriff.domain.service.auth.user;

import com.fend.moviesheriff.domain.model.User;
import com.fend.moviesheriff.domain.repository.UserRepository;
import com.fend.moviesheriff.exceptions.httpstatus.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found")));

        return new UserDetailsImpl(user);
    }
}
