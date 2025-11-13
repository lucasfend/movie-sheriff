package com.fend.moviesheriff.domain.service.persistence;

import com.fend.moviesheriff.domain.dto.userDTOs.CreateUserDTO;
import com.fend.moviesheriff.domain.dto.userDTOs.UserResponseDTO;
import com.fend.moviesheriff.domain.mapper.UserMapper;
import com.fend.moviesheriff.domain.model.MovieRating;
import com.fend.moviesheriff.domain.model.User;
import com.fend.moviesheriff.domain.repository.MovieRatingRepository;
import com.fend.moviesheriff.domain.repository.UserRepository;
import com.fend.moviesheriff.exceptions.httpstatus.BadRequestException;
import com.fend.moviesheriff.infra.dto.externalDTO.TmdbGetExternalRequestDTO;
import com.fend.moviesheriff.infra.dto.movieratingDTO.MovieRatingForUserProfileListDTO;
import com.fend.moviesheriff.infra.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fend.moviesheriff.domain.service.auth.utils.BcryptPasswordUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MovieRatingRepository movieRatingRepository;
    private final TmdbService tmdbService;
    private final BcryptPasswordUtil bcryptPasswordUtil;

    public User findByIdOrThrowException(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    public UserResponseDTO mappingExternalMovieByIdToUser(Long id) {
        List<MovieRating> allMovieRatingsWhichHaveThisUser = movieRatingRepository.findAllByUser_Id(id);

        User user = findByIdOrThrowException(id);

        List<TmdbGetExternalRequestDTO> getRequestList = new ArrayList<>();

        List<String> externalId = allMovieRatingsWhichHaveThisUser.stream().map(MovieRating::getExternalId).toList();

        for (String external : externalId) {
            getRequestList.add(tmdbService.getMovieDetailsOnExternalAPIById(external));
        }

        List<MovieRatingForUserProfileListDTO> movieToUserProfile = getRequestList.stream()
                .map(tmdbGetRequestDTO -> {
                    return MovieRatingForUserProfileListDTO.builder()
                            .externalId(tmdbGetRequestDTO.id())
                            .original_title(tmdbGetRequestDTO.original_title())
                            .poster_path(tmdbGetRequestDTO.poster_path())
                            .rating(allMovieRatingsWhichHaveThisUser.stream()
                                    .filter(movieRating -> movieRating.getExternalId().equals(tmdbGetRequestDTO.id()))
                                    .map(MovieRating::getRating)
                                    .findFirst()
                                    .orElseThrow(() -> new BadRequestException("No ratings found")))
                            .build();
                })
                .distinct()
                .toList();

        return UserResponseDTO.builder()
                .username(user.getUsername())
                .movie_ratings(movieToUserProfile)
                .build();
    }

    public void deleteUserById(Long id) {
        userRepository.delete(findByIdOrThrowException(id));
    }

    public void saveUser(CreateUserDTO createUserDTO) {
        if (userRepository.findByUsername(createUserDTO.username()).isPresent()) throw new
                BadRequestException("Username already exists");

        User userToSave = new User();
        userToSave.setUsername(createUserDTO.username());
        userToSave.setEmail(createUserDTO.email());
        String passwordHash = bcryptPasswordUtil.bcrypt().encode(createUserDTO.password());
        userToSave.setPassword(passwordHash);
        userRepository.save(userToSave);
    }

    public void updateUser(Long id, CreateUserDTO createUserDTO) {
        User existingUser = findByIdOrThrowException(id);

        existingUser.setUsername(createUserDTO.username());
        existingUser.setEmail(createUserDTO.email());

        if (createUserDTO.password() != null && !createUserDTO.password().isEmpty()) {
            existingUser.setPassword(bcryptPasswordUtil.bcrypt().encode(createUserDTO.password()));
        }

        existingUser.setMovieRatings(movieRatingRepository.findAllByUser_Id(existingUser.getId()));

        userRepository.save(existingUser);
    }
}
