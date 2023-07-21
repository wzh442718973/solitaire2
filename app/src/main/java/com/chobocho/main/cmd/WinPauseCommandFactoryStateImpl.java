package com.chobocho.main.cmd;

import com.chobocho.command.*;
import com.chobocho.main.AndroidLog;
import com.chobocho.main.BoardProfile;

public class WinPauseCommandFactoryStateImpl extends PauseCommandFactoryStateImpl implements CommandFactoryState {
    final static String TAG = "WinPauseCommandFactoryStateImpl";
    BoardProfile boardProfile;

    public WinPauseCommandFactoryStateImpl(BoardProfile profile) {
        super(profile.screenWidth(), profile.screenHeight(), profile.cardWidth(), profile.cardHeight());
        boardProfile = profile;
    }

    @Override
    public PlayCommand createCommand(int event, int x, int y) {
        AndroidLog.i(TAG, "Event:" + Integer.toString(event));
        if (event == CommandFactory.KEYPRESS_EVENT) {
            switch(x) {
                case 82: // R
                case 83: // S
                    return new PlayCommand(PlayCommand.PLAY, 0, 0);
            }
        }
        else if (event == CommandFactory.MOUSE_CLICK_EVENT) {
            for (ButtonPosition btn: buttons) {
                if (btn.isInRange(x, y)) {
                    return new PlayCommand(btn.id, 0, 0);
                }
            }
        }
        return null;
    }

    @Override
    public PlayCommand createCommand(int fromDeck, int fromPos, int toDeck, int toPos) {
        AndroidLog.i(TAG, "PauseState");
        return null;
    }

    @Override
    public void addButtons() {
        AndroidLog.i(TAG, "addButtons");

        int x1 = (screenW-400)/2;
        int y1 = (screenH-200)/2;

        buttons.push(new ButtonPosition(PlayCommand.PLAY, x1, y1-300, x1+400,y1-100));
        buttons.push(new ButtonPosition(PlayCommand.IDLE, x1, y1, x1+400,y1+200));
        buttons.push(new ButtonPosition(PlayCommand.CONFIG, x1, y1+300, x1+400,y1+500));
    }
}
