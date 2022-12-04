package com.example.chessapp.Model;

public class Knight extends Piece {
    public Knight(boolean isWhite, String rank, String file) {
        super(isWhite, rank, file);
    }

    @Override
    public boolean canMove(Board board, Square currentSquare, Square nextSquare) {
        // Move Horizontal or veritcal extended
        int[][] movements = {
                {2,1},
                {1,2},
                {-1,2},
                {-2,1},
                {-2,-1},
                {-1,-2},
                {1,-2},
                {2,-1}
        };

        int currFileIndex = currentSquare.getCol();
        int currRankIndex = currentSquare.getRow();
        int nextFileIndex = nextSquare.getCol();
        int nextRankIndex = nextSquare.getRow();
        for (int[] move : movements) {
            int nf = currFileIndex + move[0];
            int nr = currRankIndex + move[1];

            if(nf == nextFileIndex && nr == nextRankIndex){
                return true;
            }
        }

        return false;
    }
}
