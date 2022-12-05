package com.example.chessapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chessapp.GameSave;
import com.example.chessapp.R;

import java.util.ArrayList;
import java.util.List;

class GameHolder {
    public TextView titleText;
}
class GamesAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<GameSave> gameSaves;

    protected GamesAdapter(Context context, List<GameSave> gameSaves) {
        this.inflater = LayoutInflater.from(context);
        this.gameSaves = gameSaves;
    }

    @Override
    public int getCount() {
        return this.gameSaves.size();
    }

    @Override
    public GameSave getItem(int position) {
        return this.gameSaves.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.gameSaves.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final GameSave gameSave = getItem(position);

        if(convertView == null) {
            convertView = this.inflater.inflate(R.layout.move_item, parent, false);
            final GameHolder gameHolder = new GameHolder();
            gameHolder.titleText = (TextView) convertView.findViewById(R.id.tvDisplayText);
            convertView.setTag(gameHolder);
        }

        final GameHolder gameHolder = (GameHolder) convertView.getTag();
        gameHolder.titleText.setText(gameSave.getTitle());
        return convertView;
    }
}
public class HomeScreenView extends AppCompatActivity {
    private ArrayList<GameSave> gameSaves = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        Bundle bundle = getIntent().getExtras();
        GameSave game = (GameSave) bundle.getSerializable("GAME_SAVE");
        if(game != null) {
            gameSaves.add(game);
        }
        final GamesAdapter adapter = new GamesAdapter(this.getBaseContext(), gameSaves);
        ListView listView = findViewById(R.id.games_list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((p, v, pos, id) -> {
            System.out.println(p);
            Bundle previewBundle = new Bundle();
            GameSave item = (GameSave) p.getItemAtPosition(pos);
            previewBundle.putSerializable("GAME_PREVIEW", item);
            Intent intent = new Intent(this, ChessPreviewGameView.class);
            intent.putExtras(previewBundle);
            this.startActivity(intent);
        });


    }
}