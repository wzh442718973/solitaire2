package com.chobocho.main.cmd;

import com.chobocho.command.*;
import com.chobocho.main.AndroidLog;
import com.chobocho.solitaire.Solitare;


public class WinPlayCommandFactoryStateImpl extends PlayCommandFactoryStateImpl implements CommandFactoryState {
    final static String TAG = "WinPlayCommandFactoryStateImpl";
    int width = 140;
    int heigth = 210;

    @Override
    public PlayCommand createCommand(int fromDeck, int fromPos, int toDeck, int toPos) {
        if (fromDeck == toDeck) {
            return new PlayCommand(PlayCommand.OPEN, fromDeck, fromDeck);
        }
        else {
            if (fromDeck == Solitare.PLAY_DECK) {
                return null;
            }
            int count = fromPos+1;
            return new PlayCommand(PlayCommand.MOVE, fromDeck, toDeck, count);
        }
    }

    @Override
    public PlayCommand createCommand(int event, int x, int y) {
        AndroidLog.i(TAG, "Event:" + Integer.toString(event));
        if (event == CommandFactory.MOUSE_CLICK_EVENT) {
            for (ButtonPosition btn: buttons) {
                if (btn.isInRange(x, y)) {
                    return new PlayCommand(btn.id, 0, 0);
                }
            }
        }
        return null;
    }
	
	@Override
    public void addButtons() {
        AndroidLog.i(TAG, "addButtons");
        int screenW = 1080;
        int screenH = 1920-100;

        int x1 = screenW - 400;
        int y1 = screenH - 200;
        buttons.push(new ButtonPosition(PlayCommand.BACK, x1, y1, x1+180,y1+180));

        int x2 = screenW - 200;
        int y2 = screenH - 200;
        buttons.push(new ButtonPosition(PlayCommand.PAUSE, x2, y2, x2+180,y2+180));
    }
}
