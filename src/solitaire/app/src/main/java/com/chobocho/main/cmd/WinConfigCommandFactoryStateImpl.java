package com.chobocho.main.cmd;

import android.graphics.Rect;

import com.chobocho.command.*;
import com.chobocho.main.AndroidLog;
import com.chobocho.main.BoardProfile;

public class WinConfigCommandFactoryStateImpl extends PauseCommandFactoryStateImpl implements CommandFactoryState {
    final static String TAG = "WinConfigCommandFactoryStateImpl";

    BoardProfile profile;
    int[] BGList = {profile.BG0, profile.BG1, profile.BG2, profile.BG3, profile.BG4, profile.BG5, profile.BG6, profile.BG7, profile.BG8};

    public WinConfigCommandFactoryStateImpl(BoardProfile boardProfile) {
        profile = boardProfile;
    }

    @Override
    public PlayCommand createCommand(int event, int x, int y) {
        AndroidLog.i(TAG, "Event:" + Integer.toString(event));
        int width = 140;
        int height = 210;

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
                    int i = (x / width - 1)/2;
                    int j = (y / height - 1)/2;
                    int bgImage = i * 3 + j;
                    profile.setBG(BGList[bgImage]);
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

        int width = 140;
        int height = 210;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x1 = width*(i+1) + i * width;
                int y1 = height*(j+1) + j * height;
                buttons.push(new ButtonPosition(PlayCommand.PAUSE, x1, y1, x1+width,y1+height));
            }
        }

    }
}
