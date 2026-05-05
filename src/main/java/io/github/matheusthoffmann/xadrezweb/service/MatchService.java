package io.github.matheusthoffmann.xadrezweb.service;

import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessMatch;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessPiece;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessPosition;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.Color;
import io.github.matheusthoffmann.xadrezweb.domain.match.Match;
import io.github.matheusthoffmann.xadrezweb.domain.match.MatchStatus;
import io.github.matheusthoffmann.xadrezweb.domain.user.User;
import io.github.matheusthoffmann.xadrezweb.dto.board.BoardResponse;
import io.github.matheusthoffmann.xadrezweb.dto.match.MatchResponse;
import io.github.matheusthoffmann.xadrezweb.dto.match.MoveRequest;
import io.github.matheusthoffmann.xadrezweb.exception.BusinessException;
import io.github.matheusthoffmann.xadrezweb.exception.ResourceNotFoundException;
import io.github.matheusthoffmann.xadrezweb.repository.MatchRepository;
import io.github.matheusthoffmann.xadrezweb.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final GameManager gameManager;

    public MatchService(MatchRepository matchRepository,
                        UserRepository userRepository, GameManager gameManager) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.gameManager = gameManager;
    }

    public MatchResponse create(Long playerWhiteId, Long playerBlackId) {

        User white = userRepository.findById(playerWhiteId)
                .orElseThrow(() -> new ResourceNotFoundException("White player not found"));

        User black = userRepository.findById(playerBlackId)
                .orElseThrow(() -> new ResourceNotFoundException("Black player not found"));

        Match match = new Match(white, black);

        matchRepository.save(match);

        ChessMatch chessMatch = gameManager.createGame(match.getId());

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
                match.getStatus().name(),
                match.getWinner() != null ? match.getWinner().getId() : null
        );
    }

    public void makeMove(Long matchId, MoveRequest request) {

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));

        if (match.getStatus() == MatchStatus.FINISHED) {
            throw new BusinessException("Match already finished");
        }

        ChessMatch game = gameManager.getGame(matchId);

        if (game == null) {
            throw new RuntimeException("Game not initialized");
        }

        ChessPosition source = ChessPosition.fromString(request.source());
        ChessPosition target = ChessPosition.fromString(request.target());

        game.performChessMove(source, target);

        if (game.isCheckMate()) {

            User winner = game.getCurrentPlayer() == Color.WHITE
                    ? match.getPlayerBlack()
                    : match.getPlayerWhite();

            match.finish(winner);
            matchRepository.save(match);
        }
    }

    public BoardResponse getBoard(Long matchId) {

        ChessMatch game = gameManager.getGame(matchId);

        if (game == null) {
            throw new RuntimeException("Game not initialized");
        }

        ChessPiece[][] pieces = game.getPieces();

        String[][] board = new String[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (pieces[i][j] == null) {
                    board[i][j] = null;
                } else {
                    board[i][j] = pieces[i][j].toString();
                }
            }
        }

        return new BoardResponse(
                board,
                game.getCurrentPlayer().toString(),
                game.isCheck(),
                game.isCheckMate()
        );
    }
}