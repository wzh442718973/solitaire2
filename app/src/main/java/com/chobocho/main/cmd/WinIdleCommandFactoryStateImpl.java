package com.chobocho.main.cmd;

import com.chobocho.command.*;
import com.chobocho.main.AndroidLog;
import com.chobocho.main.BoardProfile;

public class WinIdleCommandFactoryStateImpl extends IdleCommandFactoryStateImpl implements CommandFactoryState {
    final static String TAG = "WinIdleCommandFactoryStateImpl";
    BoardProfile boardProfile;

    public WinIdleCommandFactoryStateImpl(BoardProfile boardProfile) {
        super(boardProfile.screenWidth(), boardProfile.screenHeight());
        this.boardProfile = boardProfile;
    }

    @Override
    public PlayCommand createCommand(int event, int x, int y) {
        AndroidLog.i(TAG, "Event:" + Integer.toString(event));
        if (event == CommandFactory.KEYPRESS_EVENT) {
            switch(x) {
                case 83: // S
                    return new PlayCommand(PlayCommand.PLAY, 0, 0);
            }
        }
        else if (event == CommandFactory.MOUSE_CLICK_EVENT) {
            AndroidLog.i(TAG, "Event:" + Integer.toString(x) + " : " + Integer.toString(y));
            for (ButtonPosition btn: buttons) {
                if (btn.isInRange(x, y)) {
                    AndroidLog.i(TAG, "FIND!");
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
        AndroidLog.i(TAG, "IdleState");
        return null;
    }

    @Override
    public void addButtons() {
        AndroidLog.i(TAG, "addButtons");
        int x1 = (screenW-400)/2;
        int y1 = (screenH-200)/2;
        buttons.push(new ButtonPosition(PlayCommand.PLAY, x1, y1, x1+400,y1+200));
        AndroidLog.i(TAG,buttons.toString());
    }
}
