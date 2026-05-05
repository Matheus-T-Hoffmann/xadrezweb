package io.github.matheusthoffmann.xadrezweb.service;

import io.github.matheusthoffmann.xadrezweb.domain.match.Match;
import io.github.matheusthoffmann.xadrezweb.domain.user.User;
import io.github.matheusthoffmann.xadrezweb.dto.match.MatchResponse;
import io.github.matheusthoffmann.xadrezweb.exception.ResourceNotFoundException;
import io.github.matheusthoffmann.xadrezweb.repository.MatchRepository;
import io.github.matheusthoffmann.xadrezweb.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public MatchService(MatchRepository matchRepository,
                        UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    public MatchResponse create(Long playerWhiteId, Long playerBlackId) {

        User white = userRepository.findById(playerWhiteId)
                .orElseThrow(() -> new ResourceNotFoundException("White player not found"));

        User black = userRepository.findById(playerBlackId)
                .orElseThrow(() -> new ResourceNotFoundException("Black player not found"));

        Match match = new Match(white, black);

        matchRepository.save(match);

        return toResponse(match);
    }

    public MatchResponse findById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));

        return toResponse(match);
    }

    private MatchResponse toResponse(Match match) {
        return new MatchResponse(
                match.getId(),
                match.getPlayerWhite().getId(),
                match.getPlayerBlack().getId(),
                match.getStatus().name()
        );
    }
}