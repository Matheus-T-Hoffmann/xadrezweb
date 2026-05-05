package io.github.matheusthoffmann.xadrezweb.dto.user;

public record UserResponse(
        Long id,
        String username,
        String email,
        Integer elo
) {}