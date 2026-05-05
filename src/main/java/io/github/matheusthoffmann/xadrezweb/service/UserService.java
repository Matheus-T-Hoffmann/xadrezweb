package io.github.matheusthoffmann.xadrezweb.service;

import io.github.matheusthoffmann.xadrezweb.domain.match.Match;
import io.github.matheusthoffmann.xadrezweb.domain.user.User;
import io.github.matheusthoffmann.xadrezweb.dto.match.MatchResponse;
import io.github.matheusthoffmann.xadrezweb.dto.user.UserRequest;
import io.github.matheusthoffmann.xadrezweb.dto.user.UserResponse;
import io.github.matheusthoffmann.xadrezweb.exception.BusinessException;
import io.github.matheusthoffmann.xadrezweb.exception.ResourceNotFoundException;
import io.github.matheusthoffmann.xadrezweb.repository.MatchRepository;
import io.github.matheusthoffmann.xadrezweb.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final MatchRepository matchRepository;

    public UserService(UserRepository repository, MatchRepository matchRepository) {
        this.repository = repository;
        this.matchRepository = matchRepository;
    }

    public UserResponse create(UserRequest request) {
        repository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new BusinessException("Email already exists");
                });

        User user = new User(
                request.username(),
                request.email(),
                request.password()
        );

        repository.save(user);
        return toResponse(user);
    }

    public UserResponse findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return toResponse(user);
    }

    public List<UserResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse update(Long id, UserRequest request) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());

        repository.save(user);
        return toResponse(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getElo()
        );
    }

    public List<MatchResponse> getUserMatches(Long userId) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Match> matches =
                matchRepository.findByPlayerWhiteOrPlayerBlack(user, user);

        return matches.stream()
                .map(MatchResponse::toMatchResponse)
                .toList();
    }
}
