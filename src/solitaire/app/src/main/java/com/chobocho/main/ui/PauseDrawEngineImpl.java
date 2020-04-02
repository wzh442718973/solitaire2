package com.chobocho.main.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.chobocho.solitaire.Solitare;

import java.util.LinkedList;

public class PauseDrawEngineImpl implements DrawEngine {
    final static String TAG = "PauseDrawEngineImpl";
    int screenW = 1080;
    int screenH = 1920;
    int width = 140;
    int height = 210;
    int cardCap = 30;

    int CARD_BG_IMAGE = 0;
    int NEW_GAME_IMAGE = 0;
    int RESUME_GAME_IMAGE = 2;
    Paint paint = new Paint();


    public PauseDrawEngineImpl() {
    }

    @Override
    public void onDraw(Canvas g, Solitare game, LinkedList<Integer> hideCard, Bitmap[] cardImages, Bitmap[] buttonImages) {

        for (int i = 6; i >= 0; --i) {
            int cap = 0;
            int x1 = 10 + width * i + 10 * i;

            for (int j = i, k = 0; j >= 0; --j, k++) {
                int y1 = 40 + height + cap;
                g.drawBitmap(cardImages[CARD_BG_IMAGE], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                cap += cardCap;
            }
        }


        g.drawBitmap(buttonImages[RESUME_GAME_IMAGE], null, new Rect( (screenW-400)/2, (screenH-200)/2-300,  (screenW-400)/2+400, (screenH-200)/2-100), paint);
        g.drawBitmap(buttonImages[NEW_GAME_IMAGE], null, new Rect( (screenW-400)/2, (screenH-200)/2+300,  (screenW-400)/2+400, (screenH-200)/2+500), paint);
    }
}
