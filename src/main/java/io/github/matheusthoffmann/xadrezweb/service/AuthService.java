package io.github.matheusthoffmann.xadrezweb.service;

import io.github.matheusthoffmann.xadrezweb.domain.user.User;
import io.github.matheusthoffmann.xadrezweb.dto.auth.LoginRequest;
import io.github.matheusthoffmann.xadrezweb.dto.auth.LoginResponse;
import io.github.matheusthoffmann.xadrezweb.exception.AuthentificationException;
import io.github.matheusthoffmann.xadrezweb.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;

    public AuthService(UserRepository repository) {
        this.repository = repository;
    }

    public LoginResponse login(LoginRequest request) {
        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> new AuthentificationException("Invalid email or password"));

        if (!user.getPassword().equals(request.password())) {
            throw new AuthentificationException("Invalid email or password");
        }

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getElo()
        );
    }
}