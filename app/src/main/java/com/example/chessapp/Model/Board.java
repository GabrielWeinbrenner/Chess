package com.example.chessapp.Model;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.example.chessapp.ChessGameListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Board implements Serializable {
    public static final int SIZE = 8;
    public Square[][] board = new Square[SIZE][SIZE];
    private PieceFactory pieceFactory = new PieceFactory();
    private boolean whiteMove = true;
    public static HashMap<String, String> INITIAL_POSITIONS;
    private ArrayList<Move> moves = new ArrayList<>();
    private int currMoveIndex = -1;
    private ChessGameListener listener;

    public Board(ChessGameListener listener) {
        this.listener = listener;
        INITIAL_POSITIONS = new HashMap<>();
        INITIAL_POSITIONS.put("A1", "rook");
        INITIAL_POSITIONS.put("B1", "knight");
        INITIAL_POSITIONS.put("C1", "bishop");
        INITIAL_POSITIONS.put("D1", "queen");
        INITIAL_POSITIONS.put("E1", "king");
        INITIAL_POSITIONS.put("F1", "bishop");
        INITIAL_POSITIONS.put("G1", "knight");
        INITIAL_POSITIONS.put("H1", "rook");
        INITIAL_POSITIONS.put("A8", "rook");
        INITIAL_POSITIONS.put("B8", "knight");
        INITIAL_POSITIONS.put("C8", "bishop");
        INITIAL_POSITIONS.put("D8", "queen");
        INITIAL_POSITIONS.put("E8", "king");
        INITIAL_POSITIONS.put("F8", "bishop");
        INITIAL_POSITIONS.put("G8", "knight");
        INITIAL_POSITIONS.put("H8", "rook");
        buildBoard();
    }

    protected Board(Parcel in) {
        whiteMove = in.readByte() != 0;
    }

    public String getPlayer() {
        return this.whiteMove ? "White" : "Black";
    }
    public void buildBoard() {
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                String currFile = Square.getFile(i);
                String currRank = Square.getRank(j);
                Square newSquare = new Square(i,j);
                Piece newPiece;
                Boolean isWhite = false;
                if(currRank.equals("1") || currRank.equals("2")){
                    isWhite = true;
                }
                if(currRank.equals("2") || currRank.equals("7")) {

                    newPiece = pieceFactory.makePiece(
                            "pawn",
                            isWhite,
                            currFile,
                            currRank
                    );
                    newSquare.setPiece(newPiece);
                    board[i][j] = newSquare;
                    continue;
                } else {
                    newPiece = pieceFactory.makePiece(
                            INITIAL_POSITIONS.getOrDefault(currFile+currRank, ""),
                            isWhite,
                            currFile,
                            currRank
                    );
                }

                newSquare.setPiece(newPiece);
                board[i][j] = newSquare;
            }
        }
    }

//    public boolean inCheck(){
//        boolean isChecked = false;
//        ArrayList<Piece> whitePieces = pieceFactory.getPieces("White");
//        King whiteKing = (King)whitePieces.stream().filter(piece -> piece.getClass().getSimpleName().equals("King")).toArray()[0];
//        ArrayList<Piece> blackPieces = pieceFactory.getPieces("Black");
//        King blackKing = (King)blackPieces.stream().filter(piece -> piece.getClass().getSimpleName().equals("King")).toArray()[0];
//
//        isChecked = whiteKing.isChecked(this, whiteKing.getFile(), whiteKing.getRank()) || blackKing.isChecked(this, blackKing.getFile(), blackKing.getRank());
//        return isChecked;
//
//    }
//
//    public boolean checkCheckmate(){
//        boolean isChecked = false;
//        ArrayList<Piece> whitePieces = pieceFactory.getPieces("White");
//        King whiteKing = (King)whitePieces.stream().filter(piece -> piece.getClass().getSimpleName().equals("King")).toArray()[0];
//        ArrayList<Piece> blackPieces = pieceFactory.getPieces("Black");
//        King blackKing = (King)blackPieces.stream().filter(piece -> piece.getClass().getSimpleName().equals("King")).toArray()[0];
//        isChecked = whiteKing.checkCheckmate(this) || blackKing.checkCheckmate(this);
//        return isChecked;
//    }

//    public Square getSquare(String file, String rank){
//        if (unmapFile(file) == -1 || unmapRank(rank) == -1) return null;
//        return board[unmapFile(file)][unmapRank(rank)];
//    }

    public void movePiece(Square oldSquare, Square newSquare, boolean isPreview){
        if(newSquare == null || oldSquare == null) return;
        Piece currPiece = oldSquare.getPiece();
        Piece capturedPiece = newSquare.getPiece();
        if(capturedPiece != null){
            pieceFactory.removePiece(capturedPiece);
        }
        currPiece.setMoved(true);
        this.whiteMove = !(this.whiteMove);

        oldSquare.setPiece(null);
        newSquare.setPiece(currPiece);
        if(!isPreview) {
            moves.add(new Move(oldSquare, newSquare, capturedPiece));
        }
        listener.onMovePiece();

    }
    public void movePiece(@NonNull Move move){
        Square initialSquare = move.getPrevSquare();
        Square nextSquare = move.getNextSquare();
        Piece capturedPiece = move.getCapturedPiece();
        if(nextSquare == null || initialSquare == null) return;
        Piece currPiece = nextSquare.getPiece();
        currPiece.setMoved(true);
        initialSquare.setPiece(currPiece);
        nextSquare.setPiece(capturedPiece);
        if(capturedPiece != null) {
            pieceFactory.addPiece(capturedPiece);

        }
        this.whiteMove = !(this.whiteMove);
        listener.onMovePiece();

    }
    public void undoMove() {
        Move lastMove = moves.get(moves.size() -1);
        movePiece(lastMove);
        moves.remove(moves.size()-1);
        listener.onMovePiece();
    }
    public boolean isValidMove(Square currSquare, Square nextSquare){
        if(currSquare == null || nextSquare == null)
            return false;
        if(currSquare.getPiece() == null)
            return false;
        if(!(currSquare.getPiece().isWhite() == this.whiteMove ))
            return false;
        if((currSquare.getPiece() != null && nextSquare.getPiece() != null) && (currSquare.getPiece().isWhite() == nextSquare.getPiece().isWhite()))
            return false;
        if(!currSquare.getPiece().canMove(this, currSquare, nextSquare))
            return false;
        return true;
    }

    public PieceFactory getPieceFactory() {
        return this.pieceFactory;
    }

    public Square[][] getBoard() {
        return this.board;
    }

    public ArrayList<Move> getMoves() {
        return this.moves;
    }

    public ArrayList<Square> getAvailableMoves(Square currentSquare) {
        ArrayList<Square> availableSquares = new ArrayList<Square>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Square possibleSquare = this.board[i][j];
                if (isValidMove(currentSquare, possibleSquare)) {
                    availableSquares.add(possibleSquare);
                }
            }
        }
        return availableSquares;
    }

    public void aiMove() {
        ArrayList<Move> finalPick = new ArrayList<Move>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Square currentSquare = this.board[i][j];
                for(Square nextSquare : getAvailableMoves(currentSquare)) {
                    finalPick.add(new Move(currentSquare, nextSquare, nextSquare.getPiece()));
                }
            }
        }
        int index = (int)(Math.random() * finalPick.size());
        Move move = finalPick.get(index);
        movePiece(move.getPrevSquare(), move.getNextSquare(), false);
    }
    public int getCurrentMoveIndex() {
        return this.currMoveIndex+1;
    }
    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public void previousMove() {
        currMoveIndex--;
        Move nextMove = this.moves.get(currMoveIndex);
        Square currentSquare = getSquare(nextMove.getPrevSquare().toString());
        Square nextSquare = getSquare(nextMove.getNextSquare().toString());

        movePiece(nextSquare, currentSquare, true);
    }

    public void nextMove() throws Exception {

        currMoveIndex++;
        if(currMoveIndex == this.moves.size()){
            throw new Exception("End of moves");
        }
        Move nextMove = this.moves.get(currMoveIndex);
        Square currentSquare = getSquare(nextMove.getPrevSquare().toString());
        Square nextSquare = getSquare(nextMove.getNextSquare().toString());

        movePiece(currentSquare, nextSquare, true);
    }

    public Square getSquare(String position) {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                    if(board[i][j].toString().equals(position)) {
                        return board[i][j];
                    }
            }
        }
        return null;
    }


//    public String toString(){
//        String returnedString = "";
//        for(int i = 0; i < SIZE; i++){
//            for(int j = 0; j < SIZE; j++){
//                returnedString+=board[j][i].toString();
//            }
//        }
//        return returnedString + "\n";
//    }
}
