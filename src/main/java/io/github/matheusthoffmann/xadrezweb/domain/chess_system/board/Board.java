package io.github.matheusthoffmann.xadrezweb.domain.chess_system.board;

public class Board {
    private final int rows;
    private final int columns;
    private final Piece[][] pieces;

    public Board (int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public Piece piece(int row, int column){
        Position position = new Position(row,column);
        if (positionExists(position)){
            return pieces[row][column];
        }
        return null;
    }

    public Piece piece(Position position){
        if (positionExists(position)){
            return pieces[position.getRow()][position.getColumn()];
        }
        return null;
    }

    public void placePiece(Piece piece, Position position){
        if (thereIsAPiece(position)){
            throw new BoardException("There is already a piece in this position: "+position);
        }
        if (piece != null){
            piece.position = position;
        }
        pieces[position.getRow()][position.getColumn()] = piece;
    }

    public Piece removePiece(Position position){
        if (thereIsAPiece(position)){
            Piece removedPiece = pieces[position.getRow()][position.getColumn()];
            pieces[position.getRow()][position.getColumn()] = null;
            return removedPiece;
        }
        return null;
    }

    public boolean positionExists(Position position){
        return position.getRow() < rows && position.getRow() >= 0 && position.getColumn() < columns && position.getColumn() >= 0;
    }

    public boolean thereIsAPiece(Position position){
        if (positionExists(position)){
            return piece(position) != null;
        }
        throw new BoardException("Position does not exist in the board: "+position);
    }

    public int getRows () {
        return rows;
    }

    public int getColumns () {
        return columns;
    }
}
