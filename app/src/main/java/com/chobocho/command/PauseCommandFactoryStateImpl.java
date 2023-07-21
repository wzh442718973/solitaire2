package com.chobocho.command;

import java.util.LinkedList;

public class PauseCommandFactoryStateImpl implements CommandFactoryState {
    protected LinkedList<ButtonPosition> buttons = new LinkedList<ButtonPosition>();

    protected  int screenW = 1080;
    protected  int screenH = 1920;
    protected  int cardWidth = 140;
    protected int cardHeigth = 210;

    public PauseCommandFactoryStateImpl(int screenW, int screenH, int cardW, int cardH) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.cardWidth = cardW;
        this.cardHeigth =  cardH;
        addButtons();
    }

    @Override
    public PlayCommand createCommand(int event, int x, int y) {
        return null;
    }
    public PlayCommand createCommand(int fromDeck, int fromPos, int toDeck, int toPos) {
        return null;
    }

    public void addButtons() {

    }
}
