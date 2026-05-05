package io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.pieces;

import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Board;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Position;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessMatch;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessPiece;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.Color;

public class Bishop extends ChessPiece {

    public Bishop (Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        chessMatch.addPieceOnTheBoard(this);
    }

    @Override
    public boolean[][] possibleMoves () {
        Board board = getBoard();
        boolean[][] moves = new boolean[board.getRows()][board.getColumns()];
        Position target = new Position();

        int[][] directions = {
                {-1,-1}, //up-left
                {1,-1}, //down-left
                {-1,1}, //up-right
                {1,1} //down-right
        };
        for (int[] dir : directions){
            target.setValues(position.getRow(), position.getColumn());
            while (true){
                target.setValues(target.getRow()+dir[0], target.getColumn()+dir[1]);

                if (!board.positionExists(target)){
                    break;
                }

                if (board.thereIsAPiece(target)){
                    if (isThereOpponentPiece(target)){
                        moves[target.getRow()][target.getColumn()] = true;
                        break;
                    }
                    break;
                }
                moves[target.getRow()][target.getColumn()] = true;
            }
        }
        return moves;
    }

    @Override
    public String toString () {
        return "B";
    }
}
