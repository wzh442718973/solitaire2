package com.chobocho.main.cmd;

import com.chobocho.command.*;
import com.chobocho.main.AndroidLog;

public class WinEndCommandFactoryStateImpl extends EndCommandFactoryStateImpl implements CommandFactoryState {
    final static String TAG = "WinEndCommandFactoryStateImpl";

    @Override
    public PlayCommand createCommand(int event, int x, int y) {
        AndroidLog.i(TAG, "Event:" + Integer.toString(event));
        if (event == CommandFactory.KEYPRESS_EVENT) {
            switch (x) {
                case 83:
                    return new PlayCommand(PlayCommand.PLAY, 0, 0);
            }
        } else if (event == CommandFactory.MOUSE_CLICK_EVENT) {
            for (ButtonPosition btn : buttons) {
                if (btn.isInRange(x, y)) {
                    if (btn.id.equals(PlayCommand.PLAY)) {
                        return new PlayCommand(btn.id, 0, 0);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public PlayCommand createCommand(int fromDeck, int fromPos, int toDeck, int toPos) {
        AndroidLog.i(TAG, "GameEndState");
        return null;
    }

    @Override
    public void addButtons() {
        AndroidLog.i(TAG, "addButtons");
        int screenW = 1080;
        int screenH = 1920;

        int x1 = (screenW-400)/2;
        int y1 = (screenH-200)/2;
        buttons.push(new ButtonPosition(PlayCommand.PLAY, x1, y1, x1+400,y1+200));
    }
}