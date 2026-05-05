package io.github.matheusthoffmann.xadrezweb.domain.chess_system.board;

import java.util.Objects;

public class Position {
    private int row;
    private int column;

    public Position(){
    }

    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }

    public void setValues(int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getRow () {
        return row;
    }

    public int getColumn () {
        return column;
    }

    @Override
    public String toString () {
        return row + ", " + column;
    }
    @Override
    public boolean equals (Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode () {
        return Objects.hash(row, column);
    }
}
