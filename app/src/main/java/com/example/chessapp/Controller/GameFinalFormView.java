package com.example.chessapp.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chessapp.GameSave;
import com.example.chessapp.Model.Move;
import com.example.chessapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ViewHolder {
    public TextView moveText;
}

class MovesAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<Move> moves;

    protected MovesAdapter(Context context, List<Move> moves) {
        this.inflater = LayoutInflater.from(context);
        this.moves = moves;
    }

    @Override
    public int getCount() {
        return this.moves.size();
    }

    @Override
    public Move getItem(int position) {
        return this.moves.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.moves.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Move move = getItem(position);

        if(convertView == null) {
            convertView = this.inflater.inflate(R.layout.move_item, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.moveText = (TextView) convertView.findViewById(R.id.tvDisplayText);
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.moveText.setText(move.toString());
        return convertView;
    }
}

class SaveGameListener implements View.OnClickListener  {
    ArrayList<Move> moves;
    EditText gameNameInput;
    Activity a;
    public SaveGameListener(ArrayList<Move> moves, EditText gameNameInput, Activity a) {
        this.moves = moves;
        this.gameNameInput = gameNameInput;
        this.a = a;
    }
    @Override
    public void onClick(View v) {
        Bundle saveGameBundle = new Bundle();
        GameSave gameSave = new GameSave(moves, new Date(), gameNameInput.getText().toString());
        saveGameBundle.putSerializable("GAME_SAVE", gameSave);
        Intent intent = new Intent(this.a, HomeScreenView.class);
        intent.putExtras(saveGameBundle);
        a.startActivity(intent);
    }
}


public class GameFinalFormView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_game_final_form);

        Bundle bundle = getIntent().getExtras();
        ArrayList<Move> moves = (ArrayList<Move>) bundle.getSerializable("MOVES");
        String status = (String) bundle.getSerializable("STATUS");
        String player = (String) bundle.getSerializable("PLAYER");
        ListView listView = findViewById(R.id.moves_list);
        EditText gameNameInput = findViewById(R.id.game_name_input);
        TextView gameStatusText = findViewById(R.id.game_status_text);
        gameStatusText.setText(player + " " + status);
        final MovesAdapter adapter = new MovesAdapter(this.getBaseContext(), moves);
        listView.setAdapter(adapter);

        Button saveButton = findViewById(R.id.start_game_button);
        saveButton.setOnClickListener(new SaveGameListener(moves, gameNameInput,this));
    }
}