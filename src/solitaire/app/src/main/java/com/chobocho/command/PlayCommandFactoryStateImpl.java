package com.chobocho.command;

import java.util.LinkedList;

public class PlayCommandFactoryStateImpl implements CommandFactoryState {
    protected LinkedList<ButtonPosition> buttons = new LinkedList<ButtonPosition>();
    protected int screenW = 1080;
    protected int screenH = 1920;

    public PlayCommandFactoryStateImpl(int width, int height) {
        screenW = width;
        screenH = height;
        addButtons();
    }

    @Override
    public PlayCommand createCommand(int event, int x, int y) {
        return null;
    }

    @Override
    public PlayCommand createCommand(int fromDeck, int fromPos, int toDeck, int toPos) {
        return null;
    }

    public void addButtons() {

    }
}
