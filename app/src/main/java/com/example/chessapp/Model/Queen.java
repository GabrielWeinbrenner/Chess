package com.example.chessapp.Model;

public class Queen extends Piece {
    public Queen(boolean isWhite, String rank, String file) {
        super(isWhite, rank, file);
    }

    @Override
    public boolean canMove(Board board, Square currentSquare, Square nextSquare) {

        int currFileIndex = currentSquare.getCol();
        int currRankIndex = currentSquare.getRow();
        int nextFileIndex = nextSquare.getCol();
        int nextRankIndex = nextSquare.getRow();

        if (Math.abs(currFileIndex - nextFileIndex) == Math.abs(currRankIndex - nextRankIndex)){
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
                if(y > 8 || y < 0 || x > 8 || x < 0){
                    break;
                }
                Piece currPiece = board.board[y][x].getPiece();
                if(currPiece != null){
                    return false;
                }
                y+=colOffset;
            }
            return true;
        } else if (currentSquare.getRow() == nextSquare.getRow()) {
            int changeX = 0;
            if (currFileIndex < nextFileIndex) {
                changeX = 1;
            } else {
                changeX = -1;
            }
            // check for collision
            for (int i = currFileIndex + changeX; i != nextFileIndex; i += changeX) {
                if (board.board[i][currRankIndex].getPiece() != null) {
                    return false;
                }
            }
        } else if (currentSquare.getCol() == nextSquare.getCol()) {
            int changeY = 0;
            if (currRankIndex < nextRankIndex) {
                changeY = 1;
            } else {
                changeY = -1;
            }
            for (int i = currRankIndex+=changeY; i != nextRankIndex; i += changeY) {
                if (board.board[currFileIndex][i].getPiece() != null) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

}
