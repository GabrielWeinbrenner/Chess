package com.example.chessapp.Model;

import java.io.Serializable;

public abstract class Piece implements Serializable {
    private boolean isKilled = false;
    private boolean isWhite = false;
    private boolean hasMoved = false;
    private String file = "";
    private String rank = "";
    public Piece(boolean white, String rank, String file) {
        isWhite = white;
        this.file = file;
        this.rank = rank;
    }
    public void move(String file, String rank) {this.file = file; this.rank = rank; this.hasMoved = true;}
    public void setMoved(boolean hasMoved) {this.hasMoved = hasMoved;}
    public void setKilled(boolean isKilled) { this.isKilled = isKilled; }
    public void setWhite(boolean isWhite) { this.isWhite = isWhite; }
    public boolean isKilled() { return this.isKilled; }
    public boolean isWhite() { return this.isWhite; }
    public boolean hasMoved() { return this.hasMoved; }
    public String getFile() { return this.file; }
    public String getRank() { return this.rank; }
    public abstract boolean canMove(Board board, Square currentSquare, Square nextSquare);
    public String toString() { return this.isWhite() ? "white_"+this.getClass().getSimpleName().toLowerCase() : "black_"+this.getClass().getSimpleName().toLowerCase();}
}