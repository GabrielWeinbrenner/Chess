package com.example.chessapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chessapp.GameSave;
import com.example.chessapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private boolean fileExists(Context _context, String _filename) {
        File temp = _context.getFileStreamPath(_filename);
        if(temp == null || !temp.exists()) {
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        try {
            FileInputStream fis = openFileInput("gameSaves.dat");
            ObjectInputStream in = new ObjectInputStream(fis);
            gameSaves = new ArrayList<GameSave>();
            for(GameSave gameSave : (ArrayList<GameSave>) in.readObject()) {
                gameSaves.add(gameSave);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("NO Saves");
        }
        Bundle bundle = getIntent().getExtras();
        GameSave game = (GameSave) bundle.getSerializable("GAME_SAVE");
        if(game != null) {
            gameSaves.add(game);
            try {
                FileOutputStream fos = null;
                fos = this.openFileOutput("gameSaves.dat", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(gameSaves);
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

        Button newGameButton = findViewById(R.id.start_game_button);

        newGameButton.setOnClickListener((v) -> {
            Intent intent = new Intent(this.getBaseContext(), ChessGame.class);
            this.startActivity(intent);
        });




    }
}