package com.chobocho.main.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.chobocho.solitaire.Solitare;

import java.util.LinkedList;

public interface DrawEngine {
    void onDraw(Canvas g, Solitare game, LinkedList<Integer> hideCard, Bitmap[] cardImages, Bitmap[] buttonImages);
}
