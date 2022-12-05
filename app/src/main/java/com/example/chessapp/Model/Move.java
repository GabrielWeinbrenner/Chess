package com.example.chessapp.Model;

import java.io.Serializable;

public class Move implements Serializable {

    private Square prevSquare;
    private Square nextSquare;
    private Piece capturedPiece;
    public Move(Square prevSquare, Square nextSquare, Piece capturedPiece)  {
        this.prevSquare = prevSquare;
        this.nextSquare = nextSquare;
        this.capturedPiece = capturedPiece;
    }

    public Square getPrevSquare() {
        return prevSquare;
    }

    public Square getNextSquare() {
        return nextSquare;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public String toString() {
        return String.format("%s %s", this.prevSquare.toString(), this.nextSquare.toString());
    }
}
