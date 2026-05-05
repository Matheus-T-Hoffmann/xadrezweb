package io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess;

import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Board;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Piece;
import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Position;

public abstract class ChessPiece extends Piece {

    private final Color color;
    private int moveCount;

    public ChessPiece (Board board, Color color) {
        super(board);
        this.color = color;
    }

    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position){
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece.getColor() != color;
    }

    protected void increasedMoveCount(){
        moveCount++;
    }

    protected void decreasedMoveCount(){
        moveCount--;
    }

    public Color getColor () {
        return color;
    }

    public int getMoveCount () {
        return moveCount;
    }
}
