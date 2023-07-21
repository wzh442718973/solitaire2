package com.chobocho.solitaire;

import com.chobocho.deck.Deck;

public class ConfigState extends GameState {
    Deck resultDeck;

    public ConfigState() {

    }

    public int getState() { return Solitare.CONFIG_STATE; }
}