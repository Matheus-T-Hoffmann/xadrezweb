package io.github.matheusthoffmann.xadrezweb.dto.match;

import io.github.matheusthoffmann.xadrezweb.domain.match.Match;

public record MatchResponse(
        Long id,
        Long playerWhiteId,
        Long playerBlackId,
        String status,
        Long winnerId
) {
    public static MatchResponse toMatchResponse (Match match) {
        return new MatchResponse(
                match.getId(),
                match.getPlayerWhite().getId(),
                match.getPlayerBlack().getId(),
                match.getStatus().name(),
                match.getWinner() != null ? match.getWinner().getId() : null
        );
    }
}