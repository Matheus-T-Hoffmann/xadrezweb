package io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.pieces;

import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Board;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Position;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessMatch;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessPiece;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.Color;

public class Knight extends ChessPiece {

    public Knight (Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        chessMatch.addPieceOnTheBoard(this);
    }

    @Override
    public boolean[][] possibleMoves () {
        Board board = getBoard();
        boolean[][] moves = new boolean[board.getRows()][board.getColumns()];
        Position target = new Position();

        int[][] directions = {
                {-2, -1}, // up-left
                {-2,  1}, // up-right
                { 2, -1}, // down-left
                { 2,  1},  // down-right
                {-1, -2}, // left-up
                {-1,  2}, // right-up
                { 1, -2}, // left-down
                { 1,  2}  // right-down
        };

        for (int[] dir : directions) {
            target.setValues(position.getRow() + dir[0], position.getColumn() + dir[1]);

            if (board.positionExists(target)){
                if (board.thereIsAPiece(target)){
                    if (isThereOpponentPiece(target)){
                        moves[target.getRow()][target.getColumn()] = true;
                    }
                }
                else {
                    moves[target.getRow()][target.getColumn()] = true;
                }
            }
        }

        return moves;
    }

    @Override
    public String toString () {
        return "N";
    }
}
