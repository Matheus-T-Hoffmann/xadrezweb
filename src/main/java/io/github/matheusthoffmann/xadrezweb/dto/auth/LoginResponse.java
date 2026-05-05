package io.github.matheusthoffmann.xadrezweb.dto.auth;

public record LoginResponse(
        Long id,
        String username,
        String email,
        Integer elo
) {}