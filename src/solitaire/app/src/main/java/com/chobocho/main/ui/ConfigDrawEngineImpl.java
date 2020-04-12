package com.chobocho.main.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.chobocho.main.BoardProfile;
import com.chobocho.solitaire.Solitare;

import java.util.LinkedList;

public class ConfigDrawEngineImpl implements DrawEngine {
    BoardProfile profile;
    Paint paint;

    public ConfigDrawEngineImpl(BoardProfile boardProfile) {
        profile = boardProfile;
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas g, Solitare game, LinkedList<Integer> hideCard, Bitmap[] cardImages, Bitmap[] buttonImages) {

        onDrawBoardCards(g, cardImages, buttonImages);
    }

    private void onDrawBoardCards(Canvas g, Bitmap[] cardImages, Bitmap[] buttonImages) {
        int x1 = 10;
        int y1 = 10;
        int width = 140;
        int height = 210;
        int CARD_BG = profile.getBG();
        g.drawBitmap(cardImages[CARD_BG], null, new Rect(x1, y1,  x1+width, y1+height), paint);
    }
}
