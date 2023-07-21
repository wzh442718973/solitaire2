package com.chobocho.main.cmd;

import com.chobocho.command.*;
import com.chobocho.main.AndroidLog;
import com.chobocho.main.BoardProfile;
import com.chobocho.solitaire.Solitare;


public class WinPlayCommandFactoryStateImpl extends PlayCommandFactoryStateImpl implements CommandFactoryState {
    final static String TAG = "WinPlayCommandFactoryStateImpl";


    public WinPlayCommandFactoryStateImpl(BoardProfile profile) {
        super(profile);;
    }

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

        int x1 = screenW - (width + cardGap) * 2;
        int y1 = screenH - (height + cardGapH);
        int buttonWidth = width;
        buttons.push(new ButtonPosition(PlayCommand.BACK, x1, y1, x1+buttonWidth,y1+buttonWidth));

        int x2 = screenW - (width + cardGap) * 1;
        int y2 = screenH - (height + cardGapH);
        buttons.push(new ButtonPosition(PlayCommand.PAUSE, x2, y2, x2+buttonWidth,y2+buttonWidth));
    }
}
