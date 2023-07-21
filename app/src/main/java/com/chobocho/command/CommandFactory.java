package com.chobocho.command;

import com.chobocho.solitaire.GameObserver;
import com.chobocho.solitaire.Solitare;
import com.chobocho.util.CLog;

public abstract class CommandFactory implements GameObserver {
    final static String TAG = "CommandFactory";

    final public static int KEYPRESS_EVENT = 1;
    final public static int MOUSE_CLICK_EVENT = 0;

    protected CommandFactoryState state;
    protected CommandFactoryState idleState;
    protected CommandFactoryState playState;
    protected CommandFactoryState pauseState;
    protected CommandFactoryState endState;
    protected CommandFactoryState configState;
    @Override
    public void updateState(int nextState) {
        CLog.i(TAG, "updateState: " + nextState);
        switch (nextState) {
            case Solitare.IDLE_STATE:
                state=idleState;
                break;
            case Solitare.PLAY_STATE:
                state=playState;
                break;
            case Solitare.PAUSE_STATE:
                state=pauseState;
                break;
            case Solitare.END_STATE:
                state=endState;
                break;
            case Solitare.CONFIG_STATE:
                state=configState;
                break;
            default:
                break;
        }
    }

    public PlayCommand CreateCommand(int event, int posX, int posY) {
        return state.createCommand(event, posX, posY);
    }

    public PlayCommand CreateCommand(int fromDeck, int fromPos, int toDeck, int toPos) {
        return state.createCommand(fromDeck, fromPos, toDeck, toPos);
    }
}
