package io.github.matheusthoffmann.xadrezweb.dto.user;

public record UserRequest(
        String username,
        String email,
        String password
) {}
