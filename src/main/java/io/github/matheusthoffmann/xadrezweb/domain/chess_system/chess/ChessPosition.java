package io.github.matheusthoffmann.xadrezweb.domain.chess_system.chess;

import io.github.matheusthoffmann.xadrezweb.domain.chess_system.board.Position;

public class ChessPosition {

    private final char column;
    private final int row;

    public ChessPosition (char column, int row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException("Invalid chess position: "+column+" "+row);
        }
        this.column = column;
        this.row = row;
    }

    protected Position toPosition(){
        return new Position(8-row,column-'a');
    }

    protected static ChessPosition fromPosition(Position position){
        return new ChessPosition((char) (position.getColumn() + 'a'), 8 - position.getRow());
    }
}
