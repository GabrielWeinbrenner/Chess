package com.example.chessapp.Model;

public class King extends Piece {
    public King(boolean isWhite, String rank, String file) {
        super(isWhite, rank, file);
    }

    int[][] offsets = {
            { 1, 0 },
            { 0, 1 },
            { -1, 0 },
            { 0, -1 },
            { 1, 1 },
            { -1, 1 },
            { -1, -1 },
            { 1, -1 }
    };

    @Override
    public boolean canMove(Board board, Square currentSquare, Square nextSquare) {
        return false;
    }
//        int currFileIndex = currentSquare.getCol();
//        int currRankIndex = currentSquare.getRow();
//        int nextFileIndex = nextSquare.getCol();
//        int nextRankIndex = nextSquare.getRow();
//
//        PieceFactory p = board.getPieceFactory();
//        ArrayList<Piece> allyPieces = p.getPieces((this.isWhite()) ? "White" : "Black");
//
//        boolean canMove = false;
//        int[][] offsets = {
//                { 1, 0 },
//                { 0, 1 },
//                { -1, 0 },
//                { 0, -1 },
//                { 1, 1 },
//                { -1, 1 },
//                { -1, -1 },
//                { 1, -1 }
//        };
//        if (!this.hasMoved()) {
//
//            if (currRankIndex == nextRankIndex && Math.abs(currFileIndex - nextFileIndex) == 2) {
//                Piece rookDest = null;
//                for (Piece piece : allyPieces) {
//                    if(piece.getClass().getSimpleName().equals("Rook")){
//                        int allyRookFile = Board.unmapFile(piece.getFile());
//                        int allyRookRank = Board.unmapRank(piece.getRank());
//                        if ((allyRookFile != nextFileIndex-1 || allyRookFile != nextFileIndex+2) && allyRookRank != nextRankIndex) {
//                            continue;
//                        } else {
//                            rookDest = piece;
//                        }
//                    }
//                }
//                if (rookDest == null)
//                    return false;
//                if (rookDest.hasMoved())
//                    return false;
//                // check in between
//                int offsetX = 0;
//                if (currFileIndex < nextFileIndex) {
//                    offsetX = 1;
//                } else {
//                    offsetX = -1;
//                }
//                for (int i = currFileIndex + offsetX; i != nextFileIndex; i += offsetX) {
//                    if (board.board[i][currRankIndex].getPiece() != null) {
//                        return false;
//                    }
//                }
//                board.movePiece(rookDest.getFile() + rookDest.getRank() + " " + Board.mapPosition(currFileIndex+offsetX, currRankIndex), false);
//                return true;
//            }
//        }
//        for (int[] move : offsets) {
//            int nf = currFileIndex + move[0];
//            int nr = currRankIndex + move[1];
//
//            if (nf == nextFileIndex && nr == nextRankIndex && board.board[nf][nr].getPiece() == null) {
//                canMove = true;
//            }
//        }
//        if (!canMove) {
//            return false;
//        }
//        if (isChecked(board, newFile, newRank))
//            canMove = false;
//
//        return canMove;
//    }
//
//
//    /** check if the king is under checkMate
//     * @param board board filled with chess
//     * @return boolean if king is under checkMate
//     */
//    public boolean checkCheckmate(Board board) {
//        if(isChecked(board, getFile(), getRank())){
//            PieceFactory p = board.getPieceFactory();
//            ArrayList<Piece> opposingPieces = p.getPieces(!(this.isWhite()) ? "White" : "Black");
//            int currFileIndex = Board.unmapFile(getFile());
//            int currRankIndex = Board.unmapRank(getRank());
//
//            ArrayList<int[]> availableMoves = new ArrayList<int[]>();
//            for (int[] move : offsets) {
//                int nf = currFileIndex + move[0];
//                int nr = currRankIndex + move[1];
//                if (nf >= 8 || nf < 0 || nr >= 8 || nr < 0)
//                    continue;
//                String sf = Board.FILES[nf];
//                String sr = Board.RANKS[nr];
//                Square currSquare = board.getSquare(sf, sr);
//                if (currSquare == null) {
//                    continue;
//                }
//                if (currSquare.getPiece() != null && (currSquare.getPiece().isWhite() == this.isWhite())) {
//                    continue;
//                }
//                availableMoves.add(move);
//            }
//            ArrayList<int[]> move = new ArrayList<int[]>();
//            for(int[] curr : availableMoves){
//                int nf = currFileIndex + curr[0];
//                int nr = currRankIndex + curr[1];
//                for (Piece piece : opposingPieces) {
//                    if (piece.canMove(board, Board.FILES[nf], Board.RANKS[nr])) {
//                        move.add(curr);
//                    }
//                }
//
//            }
//            return move.size() > 0 ? true : false;
//        }
//        return false;
//    }
//
//
//    /** return if the king is under check
//     * @param board board fille with chess
//     * @param newFile new file in string
//     * @param newRank new rank in string
//     * @return boolean  if the king is under check
//     */
//    public boolean isChecked(Board board, String newFile, String newRank) {
//        PieceFactory p = board.getPieceFactory();
//        ArrayList<Piece> opposingPieces = p.getPieces(!(this.isWhite()) ? "White" : "Black");
//        int nextFileIndex = Board.unmapFile(newFile);
//        int nextRankIndex = Board.unmapRank(newRank);
//        for (Piece piece : opposingPieces) {
//            if(!(piece.getClass().getSimpleName().equals("King"))){
//                if(piece.canMove(board, newFile, newRank)){
//                    return true;
//                }
//            } else {
//                int kingFileIndex = Board.unmapFile(piece.getFile());
//                int kingRankIndex = Board.unmapRank(piece.getRank());
//                for (int[] move : offsets) {
//                    int nf = kingFileIndex + move[0];
//                    int nr = kingRankIndex + move[1];
//
//                    if(nf == nextFileIndex && nr == nextRankIndex){
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
}
