package com.example.chessapp.Model;

public class Rook extends Piece {
    public Rook(boolean isWhite, String rank, String file) {
        super(isWhite, rank, file);
    }

    @Override
    public boolean canMove(Board board, Square currentSquare, Square nextSquare) {

        int currFileIndex = currentSquare.getCol();
        int currRankIndex = currentSquare.getRow();
        int nextFileIndex = nextSquare.getCol();
        int nextRankIndex = nextSquare.getRow();

        if (currentSquare.getCol() == nextSquare.getCol()) {
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
        } else if (currentSquare.getRow() == nextSquare.getRow()) {
            int changeX = 0;
            if (currFileIndex < nextFileIndex) {
                changeX = 1;
            } else {
                changeX = -1;
            }
            for (int i = currFileIndex + changeX; i != nextFileIndex; i += changeX) {
                if (board.board[i][currRankIndex].getPiece() != null) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
