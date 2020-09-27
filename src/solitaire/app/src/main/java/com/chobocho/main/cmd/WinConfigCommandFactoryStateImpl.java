package com.chobocho.main.cmd;

import android.graphics.Rect;

import com.chobocho.command.*;
import com.chobocho.main.AndroidLog;
import com.chobocho.main.BoardProfile;

public class WinConfigCommandFactoryStateImpl extends PauseCommandFactoryStateImpl implements CommandFactoryState {
    final static String TAG = "WinConfigCommandFactoryStateImpl";

    BoardProfile boardProfile;
    int[] BGList = {boardProfile.BG0, boardProfile.BG1, boardProfile.BG2, boardProfile.BG3, boardProfile.BG4, boardProfile.BG5, boardProfile.BG6, boardProfile.BG7, boardProfile.BG8};

    public WinConfigCommandFactoryStateImpl(BoardProfile profile) {
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
                    int i = (x / cardWidth - 1)/2;
                    int j = (y / cardHeigth - 1)/2;
                    int bgImage = i * 3 + j;
                    boardProfile.setBG(BGList[bgImage]);
                    AndroidLog.i(TAG, "New bgImage:" + Integer.toString(bgImage));
                    return new PlayCommand(btn.id, 0, 0);
                }
            }
        }
        return null;
    }

    @Override
    public PlayCommand createCommand(int fromDeck, int fromPos, int toDeck, int toPos) {
        AndroidLog.i(TAG, "ConfigState");
        return null;
    }

    @Override
    public void addButtons() {
        AndroidLog.i(TAG, "addButtons");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x1 = cardWidth + cardWidth*i*2;
                int y1 = cardWidth + cardHeigth*j*2;
                buttons.push(new ButtonPosition(PlayCommand.PAUSE, x1, y1, x1+cardWidth,y1+cardHeigth));
            }
        }

    }
}
