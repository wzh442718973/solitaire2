package com.chobocho.main.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.chobocho.card.Card;
import com.chobocho.deck.Deck;
import com.chobocho.deck.PlayDeck;
import com.chobocho.main.BoardProfile;
import com.chobocho.solitaire.Solitare;

import java.util.LinkedList;

public class EndDrawEngineImpl implements DrawEngine {
    final static String TAG = "EndDrawEngineImpl";

    int screenW = 1080;
    int screenH = 1920;

    int NEW_GAME_IMAGE = 0;
    int CARD_NONE_IMAGE = 53;

    int width = 140;
    int height = 210;
    int cardCap = 30;
    Paint paint = new Paint();

    BoardProfile boardProfile;
    public EndDrawEngineImpl(BoardProfile profile) {
        boardProfile = profile;
    }

    @Override
    public void onDraw(Canvas g, Solitare game, LinkedList<Integer> hideCard, Bitmap[] cardImages, Bitmap[] buttonImages) {
        onDrawResultDeck(g, cardImages, game);
        g.drawBitmap(buttonImages[NEW_GAME_IMAGE], null, new Rect( (screenW-400)/2, (screenH-200)/2,  (screenW-400)/2+400, (screenH-200)/2+200), paint);
    }

    private void onDrawResultDeck(Canvas g, Bitmap[] cardImages, Solitare game) {
        Deck deck = new PlayDeck();

        for (int i = 0; i < 4; i++) {
            deck = game.getDeck(0);

            if (!deck.isEmpty()) {
                Card card = deck.get(i);
                if (card == null) {
                    continue;
                }
                int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();
                int x1 = 10 + width * i + 10 * i;
                int y1 = 10;
                g.drawBitmap(cardImages[imgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
            }
        }

        g.drawBitmap(cardImages[CARD_NONE_IMAGE], null, new Rect( 70 + width * 6, 10,  (width + 10) * 7, 10 + height), paint);

    }
}
