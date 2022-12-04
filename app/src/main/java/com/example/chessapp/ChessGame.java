package com.example.chessapp;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chessapp.Model.Board;
class UndoClickListener implements OnClickListener {
    Board board;
    public UndoClickListener(Board board) {
        this.board = board;
    }
    @Override
    public void onClick(View v) {
        this.board.undoMove();
    }
}

class AIClickListener implements OnClickListener {
    Board board;
    public AIClickListener(Board board) {
        this.board = board;
    }
    @Override
    public void onClick(View v) {
        this.board.aiMove();
    }
}
public class ChessGame extends AppCompatActivity implements ChessGameListener {
    Board board;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_game);
        Board board = new Board(this);
        this.board = board;
        ChessView cv = new ChessView(this.getBaseContext(), board);
        LinearLayout listView = (LinearLayout) findViewById(R.id.chess_layout);
        listView.addView(cv);
        ImageButton undoButton = (ImageButton) findViewById(R.id.undo_button);
        ImageButton aiButton = (ImageButton) findViewById(R.id.ai_button);
        UndoClickListener undoClickListener = new UndoClickListener(this.board);
        undoButton.setOnClickListener(undoClickListener);
        AIClickListener aiClickListener = new AIClickListener(this.board);
        aiButton.setOnClickListener(aiClickListener);
//        cv.setBoard(board);
    }


    public void changeHelperText(){
        TextView helper_text = (TextView) findViewById(R.id.helper_text);
        helper_text.setText(
                board.getPlayer() + "\n Move: " + board.getMoves().size()
        );
    }

    @Override
    public void onMovePiece() {
        changeHelperText();
    }
}
