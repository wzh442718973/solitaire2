package com.chobocho.main.cmd;

import com.chobocho.card.Card;
import com.chobocho.command.CardPosition;
import com.chobocho.command.DeckPositoinManager;
import com.chobocho.deck.Deck;
import com.chobocho.main.BoardProfile;
import com.chobocho.solitaire.Solitare;

public class DeckPositoinManagerImpl extends DeckPositoinManager {
    final public static String TAG = "DeckPositoinManagerImpl";

    BoardProfile boardProfile;
    int width = 120;
    int height = 180;
    int cardGap = 10;
    int cardGapH = 30;

    public DeckPositoinManagerImpl(BoardProfile profile) {
        super();
        this.boardProfile = profile;
        init();
    }

    private void init() {
        width = boardProfile.cardWidth();
        height = boardProfile.cardHeight();
        cardGap = boardProfile.cardGap();
        cardGapH = boardProfile.cardGapH();
        for (int i = 0; i < 4; i++) {
            CardPosition deck = new CardPosition(Solitare.RESULT_DECK_1 + i, 0, cardGap + (width + cardGap) * i, cardGap, (width + cardGap) * (i + 1), cardGap + height);
            addDeckPosition(deck);
        }

        CardPosition playDeck = new CardPosition(Solitare.PLAY_DECK, 0, cardGap + (cardGap+width)*6, cardGap, (cardGap+width)*7, cardGap + height);
        addDeckPosition(playDeck);
        CardPosition openedDeck = new CardPosition(Solitare.OPENED_CARD_DECK, 0, cardGap +(cardGap+width)*5, cardGap, (cardGap+width)*6, cardGap + height);
        addDeckPosition(openedDeck);

        int startY = cardGap + cardGapH;
        for (int i = 0; i < 6; i++) {
            CardPosition deck = new CardPosition(Solitare.BOARD_DECK_1 + i, 0, cardGap + (width + cardGap) * i, startY + height, (width + cardGap) * (i + 1), startY + height*2);
            addCardPosition(deck);
        }
    }

    @Override
    public void initCardPosition(Solitare game) {
        clearCardPosition();

        if (!game.isPlayState()) {
            return;
        }

        int startY = cardGap + cardGapH;

        for (int i = 0; i < 7; i++) {
            CardPosition deck = new CardPosition(Solitare.BOARD_DECK_1 + i, 0, cardGap + (width + cardGap) * i, startY + height, (width + cardGap) * (i + 1), startY + height*2);
            addCardPosition(deck);
            Deck boardDeck = game.getDeck(Solitare.BOARD_DECK_1+i);
            if (boardDeck == null) {
                continue;
            }
            int cap = 0;
            for (int j = boardDeck.size()-1, k = 0; j >= 0; j--, k++) {
                int deckNumber = Solitare.BOARD_DECK_1 + i;
                Card card = boardDeck.get(j);
                CardPosition pos;
                if (card.isOpen()) {
                    pos = new CardPosition(deckNumber, j, cardGap + (width + cardGap) * i, startY + height + cap, (width + cardGap) * (i + 1), startY + height*2 + cap);
                    cap += cardGapH;
                    addCardPosition(pos);
                } else {
                    // pos = new CardPosition(deckNumber, j, 10 + (width + 10) * i, startY + height + cap, (width + 10) * (i + 1), startY + height*2 + cap);
                    cap += cardGapH/2;
                }
            }

        }
    }
}
