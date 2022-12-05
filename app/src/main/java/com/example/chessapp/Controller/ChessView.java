package com.example.chessapp.Controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.example.chessapp.Model.Board;
import com.example.chessapp.Model.Square;

public class ChessView extends View {

    private static final String TAG = ChessView.class.getSimpleName();

    private static final int COLS = 8;
    private static final int ROWS = 8;

    private int x0 = 0;
    private int y0 = 0;
    private static final int DEF_SQUARE_SIZE=50;
    private int squareSize=0;
    private boolean flipped = false;
    private Board board;
    private SquareView[][] squareViews;
    private Square firstSquare = null;
    private Square secondSquare = null;
    private boolean isPreview = false;

    public ChessView(Context context, Board board, Boolean isPreview) {
        super(context);
        this.isPreview = isPreview;
        this.board = board;
        squareViews = new SquareView[COLS][ROWS];
    }
    protected void onDraw(final Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        squareSize = Math.min(getSquareSizeWidth(width), getSquareSizeHeight(height));
        computeOrigins(width, height);

        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                final int xCoord = getXCoord(c);
                final int yCoord = getYCoord(r);
                Square currentSquare = board.getBoard()[c][r];
                Rect currRect = new Rect();
                SquareView newSquareView = new SquareView(this.getContext(), currentSquare);
                squareViews[c][r] = newSquareView;
                newSquareView.setTileRect(currRect);
                if(firstSquare != null && firstSquare.equals(currentSquare)) {
                    newSquareView.setHovered(true);
                }
                if(firstSquare != null && !firstSquare.equals(currentSquare) && board.getAvailableMoves(firstSquare).contains(currentSquare)) {
                    newSquareView.setAvailableMove(true);
                }
                currRect.left = xCoord;
                currRect.top = yCoord;
                currRect.right = currRect.left + squareSize;
                currRect.bottom = currRect.top + squareSize;
                newSquareView.draw(canvas);
            }
        }
        this.invalidate();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isPreview) return false;
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Touching down!");
                for (int c = 0; c < COLS; c++) {
                    for (int r = 0; r < ROWS; r++) {
                        if (squareViews[c][r].isTouched(touchX, touchY)) {
                            if(firstSquare == null) {
                                firstSquare = board.getBoard()[c][r];
                            } else {
                                secondSquare = board.getBoard()[c][r];
                                boolean validMove = board.isValidMove(firstSquare, secondSquare);
                                if(validMove) {
                                    board.movePiece(firstSquare, secondSquare, false);
                                }
                                firstSquare = null;
                                secondSquare = null;

                            }
                        }
                    }

                }
        }
        return true;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    protected int getWidth(int sqSize) {
        return sqSize * 8;
    }

    protected int getHeight(int sqSize) {
        return sqSize * 8;
    }

    private int getSquareSizeWidth(final int width) {
        return (width)/ 8;
    }

    private int getSquareSizeHeight(final int height) {
        return (height)/8;
    }

    private int getXCoord(final int x) {
        return x0 + squareSize * (flipped ? 7 - x : x);
    }

    private int getYCoord(final int y) {
        return y0 + squareSize * (flipped ? y : 7 - y);
    }

    private void computeOrigins(final int width, final int height) {
        this.x0 = (width  - squareSize *8)/2 ;
        this.y0 = (height - squareSize *8)/2;
    }
    protected int getMaxHeightPercentage() {
        return 75;
    }

}
