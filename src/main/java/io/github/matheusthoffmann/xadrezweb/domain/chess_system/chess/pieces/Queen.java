package io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.pieces;

import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Board;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Position;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessMatch;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessPiece;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.Color;

public class Queen extends ChessPiece {

    public Queen (Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        chessMatch.addPieceOnTheBoard(this);
    }

    @Override
    public boolean[][] possibleMoves () {
        Board board = getBoard();
        boolean[][] moves = new boolean[board.getRows()][board.getColumns()];
        Position target = new Position();

        int[][] directions = {
                {-1,  0}, // up
                { 1,  0}, // down
                { 0, -1}, // left
                { 0,  1}, // right
                {-1, -1}, // up-left
                {-1,  1}, // up-right
                { 1, -1}, // down-left
                { 1,  1}  // down-right
        };

        for (int[] dir : directions) {
            target.setValues(position.getRow(), position.getColumn());

            // Move in any direction indefinitely
            while (true) {
                target.setValues(target.getRow() + dir[0], target.getColumn() + dir[1]);

                if (!board.positionExists(target)) {
                    break;
                }

                if (!board.thereIsAPiece(target)) {
                    moves[target.getRow()][target.getColumn()] = true;
                } else {
                    if (isThereOpponentPiece(target)) {
                        moves[target.getRow()][target.getColumn()] = true;
                    }
                    break;
                }
            }
        }

        return moves;
    }

    @Override
    public String toString () {
        return "Q";
    }
}
