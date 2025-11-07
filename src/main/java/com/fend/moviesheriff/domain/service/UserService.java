package com.fend.moviesheriff.domain.service;

import com.fend.moviesheriff.domain.dto.userDTOs.CreateUserDTO;
import com.fend.moviesheriff.domain.dto.userDTOs.UserResponseDTO;
import com.fend.moviesheriff.domain.mapper.MovieRatingMapper;
import com.fend.moviesheriff.domain.mapper.UserMapper;
import com.fend.moviesheriff.domain.model.MovieRating;
import com.fend.moviesheriff.domain.model.User;
import com.fend.moviesheriff.domain.repository.MovieRatingRepository;
import com.fend.moviesheriff.domain.repository.UserRepository;
import com.fend.moviesheriff.exceptions.httpstatus.BadRequestException;
import com.fend.moviesheriff.infra.dto.movieratingDTO.MovieRatingForUserProfileListDTO;
import com.fend.moviesheriff.infra.dto.externalDTO.TmdbGetExternalRequestDTO;
import com.fend.moviesheriff.infra.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MovieRatingRepository movieRatingRepository;
    private final TmdbService tmdbService;
    private final MovieRatingMapper movieRatingMapper;

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

    public User saveUser(CreateUserDTO createUserDTO) {
        return userRepository.save(userMapper.toUser(createUserDTO));
    }

    public void updateUser(CreateUserDTO createUserDTO) {
        User savedUser = userRepository.findByUsername(userMapper.toUser(createUserDTO).getUsername());

        User user = userMapper.toUser(createUserDTO);
        user.setId(savedUser.getId());
        user.setMovieRatings(movieRatingRepository.findAllByUser_Id(savedUser.getId()));

        userRepository.save(user);
    }
}
