package io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.pieces;

import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Board;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Piece;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Position;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessMatch;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.ChessPiece;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess.Color;

public class Pawn extends ChessPiece {

    private final ChessMatch chessMatch;

    public Pawn (Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
        chessMatch.addPieceOnTheBoard(this);
    }

    @Override
    public boolean[][] possibleMoves () {
        Board board = getBoard();
        boolean[][] moves = new boolean[board.getRows()][board.getColumns()];
        Position target = new Position();

        int direction = (getColor() == Color.WHITE) ? -1 : 1;
        int[][] directions = {
                {direction, 0},  // forward once
                {2*direction, 0}, //forward twice
                {direction, -1}, // capture left
                {direction, 1}   // capture right
        };

        for (int[] dir : directions){
            target.setValues(position.getRow()+dir[0], position.getColumn()+dir[1]);

            if (board.positionExists(target)) {
                if (dir[1] == 0) {
                    if (!board.thereIsAPiece(target)) {
                        if (dir[0] == direction){
                            moves[target.getRow()][target.getColumn()] = true;
                        }
                        else if (dir[0] == 2*direction && getMoveCount() == 0) {
                            moves[target.getRow()][target.getColumn()] = true;
                        }
                    }
                }
                else{
                    if (board.thereIsAPiece(target)) {
                        if (isThereOpponentPiece(target)) {
                            moves[target.getRow()][target.getColumn()] = true;
                        }
                    }
                }
            }
        }

        //En Passant
        int enPassantRow = (getColor() == Color.WHITE) ? 3 : 4;
        if (position.getRow() == enPassantRow) {
            for (int colOffset : new int[]{-1, 1}) {
                Position adjacent = new Position(position.getRow(), position.getColumn() + colOffset);
                if (board.positionExists(adjacent) && board.thereIsAPiece(adjacent)) {
                    Piece sidePiece = board.piece(adjacent);
                    if (sidePiece == chessMatch.getEnPassantVulnerable() && isThereOpponentPiece(adjacent)) {
                        Position enPassantTarget = new Position(position.getRow() + direction, adjacent.getColumn());
                        moves[enPassantTarget.getRow()][enPassantTarget.getColumn()] = true;
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public String toString(){
        return "P";
    }
}
