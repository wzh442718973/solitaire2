package com.chobocho.main.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.chobocho.card.Card;
import com.chobocho.deck.Deck;
import com.chobocho.main.BoardProfile;
import com.chobocho.solitaire.Solitare;

import java.util.LinkedList;

public class PlayDrawEngineImpl implements DrawEngine {
    final static String TAG = "PlayDrawEngineImpl";
    int screenW = 1080;
    int screenH = 1920 - 100;

    int CARD_NONE_IMAGE = 53;
    int PAUSE_BUTTON = 3;
    int REVERT_BUTTON = 4;

    int width = 140;
    int height = 210;
    int cardCap = 30;
    Paint paint = new Paint();

    BoardProfile boardProfile;

    public PlayDrawEngineImpl(BoardProfile profile) {
        boardProfile = profile;
    }

    @Override
    public void onDraw(Canvas g, Solitare game, LinkedList<Integer> hideCard, Bitmap[] cardImages, Bitmap[] buttonImages) {
        onDrawBoardDeck(g, cardImages, game, hideCard);
        onDrawResultDeck(g, cardImages, game, hideCard);
        onDrawPlayDeck(g, cardImages, game, hideCard);

        int x1 = screenW - 400;
        int y1 = screenH - 200;
        g.drawBitmap(buttonImages[REVERT_BUTTON], null, new Rect(x1, y1, x1 + 180, y1 + 180), paint);

        int x2 = screenW - 200;
        int y2 = screenH - 200;
        g.drawBitmap(buttonImages[PAUSE_BUTTON], null, new Rect(x2, y2, x2 + 180, y2 + 180), paint);


        paint.setColor(Color.BLUE);
        paint.setTextSize(60);
        g.drawText("Move: " + Integer.toString(game.getMoveCount()), 50, screenH - 80, paint);
    }

    private void onDrawBoardDeck(Canvas g, Bitmap[] cardImages, Solitare game, LinkedList<Integer> hideCard) {
        int  CARD_BG_IMAGE = boardProfile.bgImage;
        Deck[] decks = new Deck[7];

        for (int i = 0; i < 7; i++) {
            decks[i] = game.getDeck(Solitare.BOARD_DECK_1+i);

            int cap = 0;
            for (int j = decks[i].size()-1, k = 0; j >= 0; --j, k++) {
                Card card = decks[i].get(j);
                if (card.isOpen()) {
                    int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();

                    if (!hideCard.contains(imgNumber)) {
                        int x1 = 10 + width * i + 10 * i;
                        int y1 = 40 + height + cap;
                        g.drawBitmap(cardImages[imgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                    }
                    cap += cardCap*2;
                } else {
                    int x1 = 10 + width * i + 10 * i;
                    int y1 = 40 + height + cap;
                    g.drawBitmap(cardImages[CARD_BG_IMAGE], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                    cap += cardCap;
                }
            }
        }
    }

    private void onDrawResultDeck(Canvas g, Bitmap[] cardImages, Solitare game, LinkedList<Integer> hideCard) {
        //WinLog.i(TAG, "onDrawResultDeck");
        Deck[] decks = new Deck[4];

        for (int i = 0; i < 4; i++) {
            decks[i] = game.getDeck(Solitare.RESULT_DECK_1+i);

            if (!decks[i].isEmpty()) {
                Card card = decks[i].top();
                //WinLog.i(TAG, decks[i].toString());
                //WinLog.i(TAG, card.toString());
                int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();
                if (!hideCard.contains(imgNumber)) {
                    int x1 = 10 + width * i + 10 * i;
                    int y1 =  10;
                    g.drawBitmap(cardImages[imgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                } else {
                    if (decks[i].size() > 1 ) {
                        Card preCard = decks[i].get(1);
                        int preImgNumber = (preCard.getFigure().getValue() - 1) * 13 + preCard.getNumber().getValue();
                        int x1 = 10 + width * i + 10 * i;
                        int y1 =  10;
                        g.drawBitmap(cardImages[preImgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                    }
                }
            }
        }
    }

    private void onDrawPlayDeck(Canvas g, Bitmap[] cardImages, Solitare game, LinkedList<Integer> hideCard) {

        Deck deck = game.getDeck(Solitare.PLAY_DECK);

        int x1 = 70 + width * 6;
        int y1 = 10;

        if (!deck.isEmpty() && deck.top().isOpen()) {
            Card card = deck.top();
            int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();
            g.drawBitmap(cardImages[imgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
        } else if (deck.isEmpty()) {
            g.drawBitmap(cardImages[CARD_NONE_IMAGE], null, new Rect(x1, y1,  x1+width, y1+height), paint);
        }

        Deck openedCardDeck = game.getDeck(Solitare.OPENED_CARD_DECK);

        if (!openedCardDeck.isEmpty() && openedCardDeck.top().isOpen()) {
            Card card = openedCardDeck.top();
            //WinLog.i(TAG, openedCardDeck.toString());
            //WinLog.i(TAG, card.toString());
            int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();

            x1 = 60 + width * 5;
            y1 = 10;

            if (!hideCard.contains(imgNumber)) {
                g.drawBitmap(cardImages[imgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
            } else {
                if (openedCardDeck.size() > 1 ) {
                    Card preCard = openedCardDeck.get(1);
                    int preImgNumber = (preCard.getFigure().getValue() - 1) * 13 + preCard.getNumber().getValue();
                    g.drawBitmap(cardImages[preImgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                }
            }
        }
    }
}
