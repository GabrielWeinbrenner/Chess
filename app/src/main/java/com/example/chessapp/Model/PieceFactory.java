package com.example.chessapp.Model;

import java.util.ArrayList;


public class PieceFactory {
    private ArrayList<Piece> blackPieces = new ArrayList<Piece>();
    private ArrayList<Piece> whitePieces = new ArrayList<Piece>();

    public ArrayList<Piece> getPieces(String type){
        return type == "White" ? whitePieces : blackPieces;
    }

    public void removePiece(Piece p){
        if(p.isWhite()){
            whitePieces.remove(p);
        } else {
            blackPieces.remove(p);
        }
    }
    public void addPiece(Piece p) {
        if(p.isWhite()){
            whitePieces.add(p);
        } else {
            blackPieces.add(p);
        }

    }

    public Piece makePiece(String pieceType, boolean isWhite, String file, String rank){
        Piece curr = null;
        if(pieceType.equalsIgnoreCase("Knight")){
            curr = new Knight(isWhite, rank, file);
        } else if (pieceType.equalsIgnoreCase("Bishop")) {
            curr = new Bishop(isWhite, rank, file);
        } else if (pieceType.equalsIgnoreCase("Rook")) {
            curr = new Rook(isWhite, rank, file);
        } else if (pieceType.equalsIgnoreCase("Queen")) {
            curr = new Queen(isWhite, rank, file);
        } else if (pieceType.equalsIgnoreCase("King")) {
            curr = new King(isWhite, rank, file);
        } else if (pieceType.equalsIgnoreCase("Pawn")) {
            curr = new Pawn(isWhite, rank, file);
        }
        if(curr != null){
            if(isWhite){
                whitePieces.add(curr);
            } else {
                blackPieces.add(curr);
            }
        }
        return curr;
    }

}
