package io.github.matheusthoffmann.xadrezweb.dto.match;

public record MatchResponse(
        Long id,
        Long playerWhiteId,
        Long playerBlackId,
        String status
) {}