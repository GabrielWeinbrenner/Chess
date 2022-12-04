package com.example.chessapp.Model;

public class Pawn extends Piece {
    private Square prevEnpassantSquare = null;
    public Pawn(boolean white, String rank, String file) {
        super(white, rank, file);
    }

    @Override
    public boolean canMove(Board board, Square currentSquare, Square nextSquare) {
        int currFileIndex = currentSquare.getCol();
        int currRankIndex = currentSquare.getRow();
        int nextFileIndex = nextSquare.getCol();
        int nextRankIndex = nextSquare.getRow();

        Piece nextPiece = null;

        if(nextSquare != null){
            nextPiece = nextSquare.getPiece();
        }
        if(nextPiece != null && currFileIndex == nextFileIndex) {
            return false;
        }
        if(nextPiece != null && nextPiece.isWhite() == this.isWhite()){
            return false;
        }
        if(!this.hasMoved() && currFileIndex == nextFileIndex && Math.abs(nextRankIndex - currRankIndex) <=2){
            if(Math.abs(nextRankIndex- currRankIndex) == 2){
                Square inbetween = board.getBoard()[currFileIndex][isWhite() ? nextRankIndex-1 : nextRankIndex+1];

                Piece inbetweenPiece = null;
                if(inbetween != null) {
                    inbetweenPiece = inbetween.getPiece();
                }
                if(inbetweenPiece != null) return false;
                this.prevEnpassantSquare = inbetween;
            }
            if(nextRankIndex > currRankIndex && isWhite()){
                return true;
            } else {
                boolean bPawnCheck = nextRankIndex < currRankIndex && !isWhite();
                return bPawnCheck;
            }
        } else if (currFileIndex == nextFileIndex && Math.abs(currRankIndex - nextRankIndex) < 2){
            if(nextRankIndex > currRankIndex && isWhite()){
                return true;
            } else {
                boolean bPawnCheck = nextRankIndex < currRankIndex && !isWhite();
                return bPawnCheck;
            }
        }
//        else if(nextFileIndex == currFileIndex - 1 || nextFileIndex == currFileIndex + 1){
//            if(nextPiece == null && (nextRankIndex == currRankIndex - 1 || nextRankIndex == currRankIndex + 1)){
//                int enpassantFile = nextFileIndex;
//                int enpassantRank = nextRankIndex;
//                if(isWhite()){
//                    enpassantRank++;
//                } else {
//                    enpassantRank--;
//                }
//                Square enpassantSquare = board.getBoard()[enpassantFile][enpassantRank];
//                if(enpassantSquare == null){
//                    return false;
//                }
//                Piece curr = enpassantSquare.getPiece();
//                if(curr != null && curr.getClass().getSimpleName().equals("Pawn")){
//                    board.getPieceFactory().removePiece(curr);
//                    enpassantSquare.setPiece(null);
//                    return true;
//                }
//                return false;
//
//            }
//            if(nextPiece != null && nextPiece.isWhite() != isWhite()){
//                return isWhite() ? nextRankIndex == currRankIndex + 1 : nextRankIndex == currRankIndex - 1;
//            }
//        }

        return false;
    }
}
