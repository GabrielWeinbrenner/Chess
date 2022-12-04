package com.example.chessapp.Model;

public class Bishop extends Piece {
    public Bishop(boolean isWhite, String rank, String file) {
        super(isWhite, rank, file);
    }

    @Override
    public boolean canMove(Board board, Square currentSquare, Square nextSquare) {
        int currFileIndex = currentSquare.getCol();
        int currRankIndex = currentSquare.getRow();
        int nextFileIndex = nextSquare.getCol();
        int nextRankIndex = nextSquare.getRow();
        if(currFileIndex == nextFileIndex || currRankIndex == nextRankIndex){
            return false;
        }
        if (Math.abs(currFileIndex - nextFileIndex) != Math.abs(currRankIndex - nextRankIndex)){
            return false;
        }
        int rowOffset, colOffset;
        if(currRankIndex < nextRankIndex){
            rowOffset = 1;
        } else {
            rowOffset = -1;
        }
        if(currFileIndex < nextFileIndex){
            colOffset = 1;
        } else {
            colOffset = -1;
        }
        int y = currFileIndex + colOffset;
        for(int x = currRankIndex + rowOffset; x != nextRankIndex; x += rowOffset){
            Piece currPiece = board.board[y][x].getPiece();
            if(currPiece != null){
                return false;
            }
            y+=colOffset;
        }

        return true;
    }
}
