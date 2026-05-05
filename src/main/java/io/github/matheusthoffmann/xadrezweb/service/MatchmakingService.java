package io.github.matheusthoffmann.xadrezweb.service;

import io.github.matheusthoffmann.xadrezweb.domain.match.Match;
import io.github.matheusthoffmann.xadrezweb.domain.user.User;
import io.github.matheusthoffmann.xadrezweb.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class MatchmakingService {

    private final Queue<User> queue = new LinkedList<>();

    private final MatchRepository matchRepository;
    private final GameManager gameManager;

    public MatchmakingService(MatchRepository matchRepository,
                              GameManager gameManager) {
        this.matchRepository = matchRepository;
        this.gameManager = gameManager;
    }

    public Match findMatch(User user) {

        if (queue.contains(user)) {
            return null;
        }

        if (queue.isEmpty()) {
            queue.add(user);
            return null;
        }

        User opponent = queue.poll();

        if (opponent.getId().equals(user.getId())) {
            queue.add(user);
            return null;
        }

        Match match = new Match(user, opponent);

        matchRepository.save(match);
        gameManager.createGame(match.getId());

        return match;
    }

    public void cancel(Long userId) {
        queue.removeIf(u -> u.getId().equals(userId));
    }
}