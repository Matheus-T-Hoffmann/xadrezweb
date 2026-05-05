package io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.pieces;

import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Board;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Position;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessMatch;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessPiece;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.Color;

public class King extends ChessPiece {


    public King (Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        chessMatch.addPieceOnTheBoard(this);
    }

    @Override
    public boolean[][] possibleMoves () {
        Board board = getBoard();
        boolean[][] moves = new boolean[board.getRows()][board.getColumns()];
        Position targetPosition = new Position();

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
            targetPosition.setValues(position.getRow() + dir[0], position.getColumn() + dir[1]);
            if (board.positionExists(targetPosition)) {
                moves[targetPosition.getRow()][targetPosition.getColumn()] = canMove(targetPosition);
            }
        }

        if (getMoveCount() == 0){
            int row = position.getRow();
            moves[row][6] = canCastleKingside(row);
            moves[row][2] = canCastleQueenside(row);
        }

        return moves;
    }

    private boolean canMove(Position position){
        if (getBoard().thereIsAPiece(position)){
            return isThereOpponentPiece(position);
        }
        return getBoard().positionExists(position);
    }

    private boolean canCastleKingside(int row){
        ChessPiece rook = (ChessPiece) getBoard().piece(row, 7);
        if (rook instanceof Rook && rook.getMoveCount() == 0) {
            return !getBoard().thereIsAPiece(new Position(row, 5)) && !getBoard().thereIsAPiece(new Position(row, 6));
        }
        return false;
    }

    private boolean canCastleQueenside(int row){
        ChessPiece rook = (ChessPiece) getBoard().piece(row, 0);
        if (rook instanceof Rook && rook.getMoveCount() == 0) {
            return !getBoard().thereIsAPiece(new Position(row, 1)) && !getBoard().thereIsAPiece(new Position(row, 2)) && !getBoard().thereIsAPiece(new Position(row, 3));
        }
        return false;
    }

    @Override
    public String toString () {
        return "K";
    }
}
