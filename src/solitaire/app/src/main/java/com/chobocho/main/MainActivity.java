package com.chobocho.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chobocho.command.CommandEngine;
import com.chobocho.solitaire.Solitare;
import com.chobocho.solitaire.SolitareImpl;

public class MainActivity extends AppCompatActivity {
    Solitare solitare = new SolitareImpl(new AndroidLog());
    CommandEngine cmdEngine = new CommandEngine(solitare);
    CardgameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(gameView);
    }

    protected void init() {
        gameView = new CardgameView(this, solitare, cmdEngine);
        solitare.register(gameView);
    }
}
