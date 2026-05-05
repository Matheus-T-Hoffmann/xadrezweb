package io.github.matheusthoffmann.xadrezweb.dto.match;

public record MatchmakingResponse(
        String status,
        Long matchId
) {}