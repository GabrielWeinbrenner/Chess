package com.example.chessapp.Controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.chessapp.Model.Square;
import com.example.chessapp.R;


public class SquareView extends View {


    private final Paint squareColor;
    private Rect tileRect;
    private Square square;
    private boolean hovered;
    private boolean availableMove;

    public SquareView(Context context, Square square) {
        super(context);
        this.square = square;
        this.squareColor = new Paint();
        squareColor.setColor(square.isDark() ? Color.rgb(118, 150, 86) : Color.rgb(238, 238, 210));
        squareColor.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(tileRect, squareColor);
        if(this.hovered == true) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.MAGENTA);
            float screenWidthPixel  = this.getResources().getDisplayMetrics().widthPixels;
            float STROKE_WIDTH = (screenWidthPixel * 0.0055f);
            paint.setStrokeWidth(STROKE_WIDTH);
            Rect outline = new Rect(tileRect);
            canvas.drawRect(outline, paint);
        }
        if(square.getPiece() != null)  {
            int currentDrawableId = getDrawableSquare(this.getContext(), square.getPiece().toString());
            Drawable drawable = getResources().getDrawable(currentDrawableId, null);
            drawable.setBounds(tileRect.left, tileRect.top, tileRect.right, tileRect.bottom);
            drawable.draw(canvas);
        } else {
            canvas.drawRect(tileRect, squareColor);
        }
        if(this.availableMove == true) {
            Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher_foreground, null);
            drawable.setBounds(tileRect.left, tileRect.top, tileRect.right, tileRect.bottom);
            drawable.draw(canvas);
        }
    }
    public void setHovered(boolean hover) {
        this.hovered = hover;
        this.invalidate();
    }
    public static int getDrawableSquare(Context context, String name) {
        if(name.equals("")) {
            return -1;
        }
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());

    }
    public boolean isTouched(final int x, final int y) {
        return tileRect.contains(x, y);
    }
    public void setTileRect(final Rect tileRect) {
        this.tileRect = tileRect;
    }

    public void setAvailableMove(boolean availableMove) {
       this.availableMove = availableMove;
       this.invalidate();
    }
}