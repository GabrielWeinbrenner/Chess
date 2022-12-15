package com.example.chessapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chessapp.ChessGameListener;
import com.example.chessapp.GameSave;
import com.example.chessapp.Model.Board;
import com.example.chessapp.R;

public class ChessPreviewGameView extends AppCompatActivity implements ChessGameListener {
    private Board board ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_preview_game);
        Bundle bundle = getIntent().getExtras();
        GameSave gameSave = (GameSave) bundle.getSerializable("GAME_PREVIEW");

        Board board = new Board(this);
        this.board = board;
        this.board.setMoves(gameSave.getMoves());
        ChessView cv = new ChessView(this.getBaseContext(), board, true);
        LinearLayout listView = (LinearLayout) findViewById(R.id.chess_layout);
        listView.addView(cv);
        ImageButton previousMoveButton = findViewById(R.id.last_move);
        ImageButton nextMoveButton = findViewById(R.id.next_move);
        previousMoveButton.setOnClickListener((v) -> {
            try {
                this.board.previousMove();
            } catch (Exception e) {
                CharSequence text = e.toString();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this.getBaseContext(), text, duration);
                toast.show();
                e.printStackTrace();
            }
            cv.setBoard(this.board);
            cv.invalidate();
        });
        nextMoveButton.setOnClickListener((v) -> {
            try {
                this.board.nextMove();
            } catch (Exception e) {
                CharSequence text = e.toString();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this.getBaseContext(), text, duration);
                toast.show();
            }
            cv.setBoard(this.board);
            cv.invalidate();
        });
        Button endGameButton = findViewById(R.id.draw_game_button);

        endGameButton.setOnClickListener((v) -> {
            Bundle emptyBundle = new Bundle();

            Intent intent = new Intent(this.getBaseContext(), HomeScreenView.class);
            intent.putExtras(emptyBundle);
            this.startActivity(intent);
        });
    }
    public void changeHelperText(){
        TextView helper_text = (TextView) findViewById(R.id.helper_text);
        helper_text.setText(
                board.getPlayer() + "\n Move: " + board.getCurrentMoveIndex() + "/" + board.getMoves().size()
        );
    }

    @Override
    public void onMovePiece() {
        changeHelperText();
    }
}