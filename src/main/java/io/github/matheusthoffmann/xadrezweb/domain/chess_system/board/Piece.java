package io.github.matheusthoffmann.xadrezweb.domain.chess_system.board;

public abstract class Piece {

    protected Position position;
    private final Board board;

    public Piece (Board board) {
        this.board = board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position){
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove(){
        boolean[][] moves = possibleMoves();
        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves[i].length; j++) {
                if (moves[i][j]){
                    return true;
                }
            }
        }
        return false;
    }

    protected Board getBoard () {
        return board;
    }
}
