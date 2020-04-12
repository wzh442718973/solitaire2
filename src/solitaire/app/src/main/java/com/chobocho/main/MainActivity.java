package com.chobocho.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.chobocho.command.CommandEngine;
import com.chobocho.solitaire.Solitare;
import com.chobocho.solitaire.SolitareImpl;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";

    Solitare solitare;
    CommandEngine cmdEngine;
    CardgameView gameView;
    BoardProfile boardProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(gameView);
    }

    protected void init() {
        String version = getVersion();
        boardProfile = new BoardProfile(version);
        solitare = new SolitareImpl(new AndroidLog());
        cmdEngine = new CommandEngine(solitare);
        gameView = new CardgameView(this, boardProfile, solitare, cmdEngine);
        solitare.register(gameView);
    }

    private String getVersion() {
        try {
            PackageInfo pkgInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pkgInfo.versionName;
            Log.i(TAG, "Version Name : "+ version);
            return version;
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, e.toString());
        }
        return "";
    }
}
