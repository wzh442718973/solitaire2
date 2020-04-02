package com.chobocho.main.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.chobocho.main.AndroidLog;
import com.chobocho.solitaire.Solitare;

import java.util.LinkedList;

public class IdleDrawEngineImpl implements DrawEngine {
    final static String TAG = "IdleDrawEngineImpl";
    int screenW = 1080;
    int screenH = 1920;
    int PLAY_GAME_IMAGE = 1;
    int CARD_BG_IMAGE = 0;

    int width = 140;
    int height = 210;
    int cardCap = 30;

    Paint paint = new Paint();

    @Override
    public void onDraw(Canvas g, Solitare game, LinkedList<Integer> hideCard, Bitmap[] cardImages, Bitmap[] buttonImages) {
        onDrawBoardDeck(g, cardImages, game);

        int x1 = (screenW-400)/2;
        int y1 = (screenH-200)/2;
        g.drawBitmap(buttonImages[PLAY_GAME_IMAGE], null, new Rect( x1,  y1, x1+400, y1+200), paint);
        AndroidLog.i(TAG, "Event:" + Integer.toString(x1) + " : " + Integer.toString(y1));
    }

    private void onDrawBoardDeck(Canvas g, Bitmap[] cardImages, Solitare game) {
        for (int i = 6; i >= 0; --i) {
            int cap = 0;
            int x1 = 10 + width * i + 10 * i;
            int y1 = 40 + height + cap;

            for (int j = i, k = 0; j >= 0; --j, k++) {
                g.drawBitmap(cardImages[CARD_BG_IMAGE], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                cap += cardCap;
            }
        }
    }


}
