package com.example.chessapp.Model;


import java.io.Serializable;

public class Square implements Serializable {
    private final int col;
    private final int row;
    private Piece currentPiece;
    public Square(final int col, final int row) {
        this.col = col;
        this.row = row;
    }

    public void setPiece(Piece piece) {
        this.currentPiece = piece;
    }

    public Piece getPiece() {
        return this.currentPiece;
    }
    public int getCol() { return this.col; }
    public int getRow() { return this.row; }
    public static String getFile(int col) {
        switch (col) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
            default:
                return null;
        }
    }

    public static String getRank(int row) {
        return String.valueOf(row + 1);
    }

    public boolean isDark() {
        return (col + row) % 2 == 0;
    }

    public String toString() {
        final String column = getFile(this.col);
        final String row = getRank(this.row);
        return column + "" + row;
    }
}
