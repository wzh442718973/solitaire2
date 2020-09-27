package com.chobocho.main.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.chobocho.main.BoardProfile;
import com.chobocho.solitaire.Solitare;

import java.util.LinkedList;

public class CommonDrawEngineImpl implements DrawEngine {
    BoardProfile boardProfile;
    int screenW = 1080;
    int screenH = 1920;

    int width = 140;
    int height = 210;
    int cardCapH = 30;

    public CommonDrawEngineImpl(BoardProfile profile) {
        boardProfile = profile;
        screenW = profile.screenWidth();
        screenH = profile.screenHeight();
        width = profile.cardWidth();
        height = profile.cardHeight();
        cardCapH = profile.cardGapH();
    }

    @Override
    public void onDraw(Canvas g, Solitare game, LinkedList<Integer> hideCard, Bitmap[] cardImages, Bitmap[] buttonImages) {
        onDrawCommon(g, cardImages, buttonImages);
    }

    private void onDrawCommon(Canvas g, Bitmap[] cardImages, Bitmap[] buttonImages) {
        int CARD_BG_IMAGE = boardProfile.getBG();
        int CARD_NONE_IMAGE = 53;
        int CARD_ABG_IMAGE = 54;

        Paint paint = new Paint();
        paint.setColor(Color.rgb(88, 214, 141));
        g.drawRect(0, 0, g.getWidth(), g.getHeight(), paint);

        int cardGap = boardProfile.cardGap();

        // Result Deck
        for (int i = 0; i < 4; i++) {
            g.drawBitmap(cardImages[CARD_ABG_IMAGE], null, new Rect(cardGap + (width + cardGap) * i, cardGap,  (width + cardGap) * (i+1), cardGap + height), paint);
        }

        // Empty Deck
        g.drawBitmap(cardImages[CARD_BG_IMAGE], null, new Rect( cardGap+(cardGap + width) * 6, cardGap,  (width + cardGap) * 7, cardGap + height), paint);

        int startY = cardGap + cardCapH;
        for (int i = 0; i < 7; i++) {
            g.drawBitmap(cardImages[CARD_NONE_IMAGE], null, new Rect(cardGap + (width + cardGap) * i,  startY + height,  (width + cardGap) * (i+1), startY + height*2 ), paint);
        }

    }
}
