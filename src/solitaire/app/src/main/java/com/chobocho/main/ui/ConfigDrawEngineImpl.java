package com.chobocho.main.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.chobocho.main.BoardProfile;
import com.chobocho.solitaire.Solitare;

import java.util.LinkedList;

public class ConfigDrawEngineImpl implements DrawEngine {
    BoardProfile profile;
    Paint paint;
    int width = 140;
    int height = 210;

    public ConfigDrawEngineImpl(BoardProfile boardProfile) {
        profile = boardProfile;
        width = profile.cardWidth();
        height = profile.cardHeight();
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas g, Solitare game, LinkedList<Integer> hideCard, Bitmap[] cardImages, Bitmap[] buttonImages) {
        onDrawBoardCards(g, cardImages, buttonImages);
    }

    private void onDrawBoardCards(Canvas g, Bitmap[] cardImages, Bitmap[] buttonImages) {
        paint.setColor(Color.rgb(88, 214, 141));
        g.drawRect(0, 0, g.getWidth(), g.getHeight(), paint);

        int CARD_BG = profile.getBG();

        int[] BGList = {profile.BG0, profile.BG1, profile.BG2, profile.BG3, profile.BG4, profile.BG5, profile.BG6, profile.BG7, profile.BG8};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x1 = width + width*i*2;
                int y1 = width + height*j*2;
                g.drawBitmap(cardImages[BGList[i*3+j]], null, new Rect(x1, y1, x1 + width, y1 + height), paint);
            }
        }
    }
}
