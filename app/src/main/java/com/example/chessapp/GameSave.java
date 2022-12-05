package com.example.chessapp;

import com.example.chessapp.Model.Move;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GameSave implements Serializable {
    private ArrayList<Move> moves;
    private String title;
    private Date createDate;
    public GameSave(ArrayList<Move> moves, Date createDate, String title ) {
        this.moves = moves;
        this.createDate = createDate;
        this.title = title;
    }


    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
