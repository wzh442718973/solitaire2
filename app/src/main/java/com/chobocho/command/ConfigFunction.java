package com.chobocho.command;

import com.chobocho.solitaire.Solitare;

class ConfigFunction implements ComandFunction {
    @Override
    public boolean run(Solitare game, int from, int to, int count) {
        return game.config();
    }
}
