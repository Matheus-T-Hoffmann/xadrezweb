package io.github.matheusthoffmann.xadrezweb.dto.match;

public record MoveRequest(
        String source,
        String target
) {}