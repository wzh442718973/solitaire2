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
    int screenH = 1920;

    int CARD_NONE_IMAGE = 53;
    int PAUSE_BUTTON = 3;
    int REVERT_BUTTON = 4;

    int width = 140;
    int height = 210;
    int cardCap = 10;
    int cardCapH = 30;
    Paint paint = new Paint();

    BoardProfile boardProfile;

    public PlayDrawEngineImpl(BoardProfile profile) {
        boardProfile = profile;
        screenW = profile.screenWidth();
        screenH = profile.screenHeight();
        width = profile.cardWidth();
        height = profile.cardHeight();
        cardCap = profile.cardGap();
        cardCapH = profile.cardGapH();
    }

    @Override
    public void onDraw(Canvas g, Solitare game, LinkedList<Integer> hideCard, Bitmap[] cardImages, Bitmap[] buttonImages) {
        onDrawBoardDeck(g, cardImages, game, hideCard);
        onDrawResultDeck(g, cardImages, game, hideCard);
        onDrawPlayDeck(g, cardImages, game, hideCard);

        int x1 = screenW - (width + cardCap) * 2;
        int y1 = screenH - (height + cardCapH);
        int buttonWidth = width;
        g.drawBitmap(buttonImages[REVERT_BUTTON], null, new Rect(x1, y1, x1 + buttonWidth, y1 + buttonWidth), paint);

        int x2 = screenW - (width + cardCap) * 1;
        int y2 = screenH - (height + cardCapH);
        g.drawBitmap(buttonImages[PAUSE_BUTTON], null, new Rect(x2, y2, x2 + buttonWidth, y2 + buttonWidth), paint);

        paint.setTextSize(cardCapH);
        paint.setColor(Color.BLUE);

        g.drawText("Move: " + Integer.toString(game.getMoveCount()), cardCap, screenH - width, paint);
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
                        int x1 = cardCap + width * i + cardCap * i;
                        int y1 = cardCap + cardCapH + height + cap;
                        g.drawBitmap(cardImages[imgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                    }
                    cap += cardCapH;
                } else {
                    int x1 = cardCap + width * i + cardCap * i;
                    int y1 = cardCap + cardCapH + height + cap;
                    g.drawBitmap(cardImages[CARD_BG_IMAGE], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                    cap += cardCapH/2;
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
                    int x1 = cardCap + width * i + cardCap * i;
                    int y1 = cardCap;
                    g.drawBitmap(cardImages[imgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                } else {
                    if (decks[i].size() > 1 ) {
                        Card preCard = decks[i].get(1);
                        int preImgNumber = (preCard.getFigure().getValue() - 1) * 13 + preCard.getNumber().getValue();
                        int x1 = cardCap + width * i + cardCap * i;
                        int y1 = cardCap;
                        g.drawBitmap(cardImages[preImgNumber], null, new Rect(x1, y1,  x1+width, y1+height), paint);
                    }
                }
            }
        }
    }

    private void onDrawPlayDeck(Canvas g, Bitmap[] cardImages, Solitare game, LinkedList<Integer> hideCard) {

        Deck deck = game.getDeck(Solitare.PLAY_DECK);

        int x1 = cardCap + (cardCap+width) * 6;
        int y1 = cardCap ;

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

            x1 = cardCap + (cardCap + width) * 5;
            y1 = cardCap;

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
