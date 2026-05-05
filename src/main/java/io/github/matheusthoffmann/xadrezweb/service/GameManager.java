package io.github.matheusthoffmann.xadrezweb.service;

import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessMatch;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameManager {

    private final Map<Long, ChessMatch> games = new HashMap<>();

    public ChessMatch createGame(Long matchId) {
        ChessMatch game = new ChessMatch();
        games.put(matchId, game);
        return game;
    }

    public ChessMatch getGame(Long matchId) {
        return games.get(matchId);
    }
}