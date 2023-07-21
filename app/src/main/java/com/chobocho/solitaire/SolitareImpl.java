package com.chobocho.solitaire;


import com.chobocho.card.Card;
import com.chobocho.deck.Deck;
import com.chobocho.util.CLog;

import java.util.ArrayList;

public class SolitareImpl implements Solitare {
    private static final String TAG = "SolitareImpl";
    GameState state;
    IdleState idleState;
    PlayState playState;
    PauseState pauseState;
    EndState endState;
    ConfigState configState;
    ArrayList<GameObserver> observers = new ArrayList<>();
    CLog log;

    public SolitareImpl(CLog log) {
        this.log = log;
        idleState = new IdleState();
        playState = new PlayState();
        pauseState = new PauseState();
        endState = new EndState();
        configState = new ConfigState();

        setState(Solitare.IDLE_STATE);
    }

    public void register(GameObserver observer) {
        this.observers.add(observer);
        observer.updateState(state.getState());
    }

    public void notifyToOberver() {
        for (GameObserver observer: observers) {
            observer.updateState(state.getState());
        }
    }

    public Deck getDeck(int deck) {
        return state.getDeck(deck);
    }

    public boolean moveCard(int from, int to, int count) {
        CLog.i(TAG,"Try to move card from " + from + " to " + to + " count " + count);
        return state.moveCard(from, to, count);
    }

    public boolean revert() {
        return state.revert();
    }

    public boolean play() {
        return setState(Solitare.PLAY_STATE);
    }

    public boolean pause() {
        return setState(Solitare.PAUSE_STATE);
    }

    public boolean winState() {
        return setState(Solitare.END_STATE);
    }
    public boolean idle() {
        return setState(Solitare.IDLE_STATE);
    }

    @Override
    public boolean config() {
        return setState(Solitare.CONFIG_STATE);
    }

    public boolean openDeck(int deck) {
        CLog.i(TAG,"openDeck: " + deck);
        return state.openCard(deck);
    }

    private boolean setState(int newState) {
        switch (newState) {
            case Solitare.IDLE_STATE:
                playState.initGame();
                state = idleState;
                break;
            case Solitare.PLAY_STATE:
                state = playState;
                break;
            case Solitare.PAUSE_STATE:
                state = pauseState;
                break;
            case Solitare.END_STATE:
                Card[] cards = new Card[4];
                cards[0] = playState.getDeck(Solitare.RESULT_DECK_1).top();
                cards[1] = playState.getDeck(Solitare.RESULT_DECK_2).top();
                cards[2] = playState.getDeck(Solitare.RESULT_DECK_3).top();
                cards[3] = playState.getDeck(Solitare.RESULT_DECK_4).top();
                playState.initGame();
                state = endState;
                state.pushCard(cards);
                break;
            case Solitare.CONFIG_STATE:
                state = configState;
                break;
            default:
                break;
        }
        notifyToOberver();
        return true;
    }

    public boolean isPlayState() {
        return state.isPlayState();
    }

    public boolean isFinishGame() {
        return state.isFinishGame();
    }

    public int getMoveCount() { return state.getMoveCount(); }

    public boolean isMovableDeck(int deck) {
        return state.isMovableDeck(deck);
    }
}