package com.chobocho.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.chobocho.card.Card;
import com.chobocho.command.CardPosition;
import com.chobocho.command.CommandEngine;
import com.chobocho.command.CommandFactory;
import com.chobocho.command.DeckPositoinManager;
import com.chobocho.command.PlayCommand;
import com.chobocho.main.cmd.DeckPositoinManagerImpl;
import com.chobocho.main.ui.CommonDrawEngineImpl;
import com.chobocho.main.ui.ConfigDrawEngineImpl;
import com.chobocho.main.ui.DrawEngine;
import com.chobocho.main.ui.EndDrawEngineImpl;
import com.chobocho.main.ui.IdleDrawEngineImpl;
import com.chobocho.main.ui.PauseDrawEngineImpl;
import com.chobocho.main.ui.PlayDrawEngineImpl;
import com.chobocho.solitaire.GameObserver;
import com.chobocho.solitaire.Solitare;

import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;

public class CardgameView extends View implements GameObserver {
    private String LOG_TAG = this.getClass().getName();
    private Context mContext;

    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private boolean isSetScale = false;
    private boolean needScaleCanvas = false;

    private BoardProfile profile;
    private Solitare solitare;
    private CommandEngine cmdEngine;

    private DrawEngine drawEngine;
    private DrawEngine idleDrawEngine;
    private DrawEngine playDrawEngine;
    private DrawEngine pauseDrawEngine;
    private DrawEngine endDrawEngine;
    private DrawEngine commonDrawEngine;
    private DrawEngine configDrawEngine;
    private CommandFactory commandFactory;
    private DeckPositoinManager deckPositoinManager;

    Bitmap[] cardImages;
    Bitmap[] buttonImage;

    private static final int EMPTY_MESSAGE = 0;
    private HandlerThread playerHandlerThread;
    private Handler playerHandler;
    private int gameSpeed = 1000;
    private int highScore = 0;

    public CardPosition StartPos;
    public CardPosition EndPos;
    public int currentMouseX = 0;
    public int currentMouseY = 0;
    public int mouseDx;
    public int mouseDy;

    private boolean isMovingCard = false;
    public LinkedList<Integer> hideCard = new LinkedList<Integer>();

    public CardgameView(Context context, BoardProfile profile, Solitare solitare, CommandEngine cmdEngine) {
        super(context);
        this.mContext = context;
        this.profile = profile;
        this.solitare = solitare;
        this.cmdEngine = cmdEngine;
        isSetScale = false;
        needScaleCanvas = false;
        loadImage();

        this.idleDrawEngine = new IdleDrawEngineImpl(profile);
        this.playDrawEngine = new PlayDrawEngineImpl(profile);
        this.pauseDrawEngine = new PauseDrawEngineImpl(profile);
        this.endDrawEngine = new EndDrawEngineImpl(profile);
        this.commonDrawEngine = new CommonDrawEngineImpl(profile);
        this.configDrawEngine = new ConfigDrawEngineImpl(profile);
        this.deckPositoinManager = new DeckPositoinManagerImpl();

        drawEngine = this.idleDrawEngine;

        commandFactory = new AndroidCommandFactory(profile);
        this.solitare.register(commandFactory);

       //createPlayerThread();
    }

    private void loadImage() {
        cardImages  = new Bitmap[profile.imageName.length+1];
        for (int i = 0; i < profile.imageName.length; i++) {
            cardImages[i] = BitmapFactory.decodeResource(mContext.getResources(), profile.imageName[i]);
        }

        buttonImage = new Bitmap[profile.buttonImageName.length+1];
        for (int i = 0; i < profile.buttonImageName.length; i++) {
            buttonImage[i] = BitmapFactory.decodeResource(mContext.getResources(), profile.buttonImageName[i]);
        }
    }

    private void createPlayerThread() {
        Log.d(LOG_TAG,"createPlayerThread");
//        playerHandlerThread = new HandlerThread("Player Processing Thread");
//        playerHandlerThread.start();
//        playerHandler = new Handler(playerHandlerThread.getLooper()){
//            @Override
//            public void handleMessage(Message msg){
//                if (solitare != null && solitare.isPlayState()) {
//                    if (playerHandler.hasMessages(EMPTY_MESSAGE)) {
//                        playerHandler.removeMessages(EMPTY_MESSAGE);
//                    }
//                    playerHandler.sendEmptyMessageDelayed(EMPTY_MESSAGE, gameSpeed);
//                }
//            }
//        };
    }

    public void startGame() {
        playerHandler.sendEmptyMessage(EMPTY_MESSAGE);
    }

    public void pauseGame() {
        Log.d(LOG_TAG, "pauseGame");
        if (solitare != null && solitare.isPlayState()) {
            solitare.pause();
        }
        saveConfig();
    }

    public void resumeGame() {
        Log.d(LOG_TAG, "resumeGame");
        //createPlayerThread();
        loadConfig();
    }

    public void update() {
        Log.d(LOG_TAG, "View.update()");
        invalidate();
    }

    public void updateState(int state) {
        Log.i(LOG_TAG, "STATE: " + state);
        switch (state) {
            case Solitare.IDLE_STATE:
                drawEngine = idleDrawEngine;
                break;
            case Solitare.PLAY_STATE:
                drawEngine = playDrawEngine;
                break;
            case Solitare.PAUSE_STATE:
                drawEngine = pauseDrawEngine;
                break;
            case Solitare.END_STATE:
                drawEngine = endDrawEngine;
                break;
            case Solitare.CONFIG_STATE:
                drawEngine = configDrawEngine;
                break;
            default:
                break;
        }
        invalidate();
    }

    public void onDraw(Canvas canvas) {
        if (solitare == null) {
            return;
        }

        Paint paint = new Paint();

        if (!isSetScale) {
            scaleX = canvas.getWidth() / 1080f;
            scaleY = canvas.getHeight() / 1920f;
            isSetScale = true;

            if (scaleX <= 0.999f) {
                needScaleCanvas = true;
                Log.d(LOG_TAG, "Resolution of device is smaller than 1080");
            }
        }

        if (needScaleCanvas) {
            canvas.scale(scaleX, scaleY);
        }

        commonDrawEngine.onDraw(canvas, solitare, hideCard, cardImages, buttonImage);
        drawEngine.onDraw(canvas, solitare, hideCard, cardImages, buttonImage);

        int width = 120;
        int height = 180;
        if (isMovingCard) {
            for (int i = 0; i < hideCard.size(); i++) {
                int px = currentMouseX - mouseDx;
                int py = (currentMouseY - mouseDy) + i * 60;
                canvas.drawBitmap(cardImages[hideCard.get(i)], null, new Rect(px, py,  px+width, py+height), paint);
            }
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        Log.d(LOG_TAG, ">> X: " + event.getX() + " Y: " + event.getY());

        if (solitare == null) {
            return false;
        }

        Log.d(LOG_TAG, ">> scaleX: " + scaleX + " scaleY: " + scaleY);

        int x = (int) (event.getX());
        int y = (int) (event.getY());

        if (needScaleCanvas) {
            x = (int) (x / scaleX);
            y = (int) (y / scaleY);
        }

        Log.d(LOG_TAG, ">> X: " + x + " Y: " + y);

        Log.d(LOG_TAG, Integer.toString(event.getAction()));

        if (MotionEvent.ACTION_UP == event.getAction()) {
            onTouchReleased(x, y);
        }

        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            onTouchPressed(x, y);
        }

        if (MotionEvent.ACTION_MOVE == event.getAction()) {
            onTouchMove(x, y);
        }
        return true;
    }

    private void onTouchPressed(int mouseX, int mouseY) {
        Log.i(LOG_TAG, "Mouse Pressed " + mouseX + ":" + mouseY);
        mouseDx = 0;
        mouseDy = 0;

        deckPositoinManager.initCardPosition(solitare);

        StartPos = deckPositoinManager.getCardInfo(mouseX, mouseY);

        if (StartPos != null) {
            if (!solitare.isMovableDeck(StartPos.deck)) {
                isMovingCard = false;
                return;
            }
            makeHideCardList();
            isMovingCard = true;
            mouseDx = mouseX - StartPos.x1;
            mouseDy = mouseY - StartPos.y1;
            Log.i(LOG_TAG, "StartDeck :" + StartPos.toString());
        }
    }

    private boolean runCommand(CardPosition pos) {
        if (pos == null) {
            return false;
        }
        PlayCommand cmd = commandFactory.CreateCommand(StartPos.deck, StartPos.position, pos.deck, pos.position);

        if (cmdEngine.runCommand(cmd)) {
            update();
            return true;
        }

        return false;
    }

    private boolean onCheckCardClickEvent(int mouseX, int mouseY) {
        Log.i(LOG_TAG, "onCheckCardClickEvent " + mouseX + ":" + mouseY);
        EndPos = deckPositoinManager.getCardInfo(mouseX, mouseY);

        if (EndPos == null) {
            return false;
        }

        if (EndPos.deck == Solitare.RESULT_DECK_1 ||
                EndPos.deck == Solitare.RESULT_DECK_2 ||
                EndPos.deck == Solitare.RESULT_DECK_3 ||
                EndPos.deck == Solitare.RESULT_DECK_4) {
            return false;
        }

        if (solitare.isMovableDeck(EndPos.deck) && (StartPos.deck == EndPos.deck)) {
            for (int i = 0; i < 4; i++) {
                PlayCommand moveCmd = commandFactory.CreateCommand(EndPos.deck, 0, i + Solitare.RESULT_DECK_1, 0);
                if (cmdEngine.runCommand(moveCmd)) {
                    return true;
                }
            }

            for (int i = 0; i < 7; i++) {
                if (EndPos.deck == (i+Solitare.BOARD_DECK_1)) {
                    continue;
                }
                PlayCommand moveCmd = commandFactory.CreateCommand(EndPos.deck, 0, i + Solitare.BOARD_DECK_1, 0);
                if (cmdEngine.runCommand(moveCmd)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void onTouchReleased(int mouseX, int mouseY) {
        Log.i(LOG_TAG, "Mouse released " + mouseX + ":" + mouseY);
        isMovingCard = false;

        // Press button event
        if (StartPos == null) {
            PlayCommand cmd = commandFactory.CreateCommand(CommandFactory.MOUSE_CLICK_EVENT, mouseX, mouseY);
            if (cmdEngine.runCommand(cmd)) {
                update();
            }
            return;
        }

        Log.i(LOG_TAG, "Mouse released " + mouseX + ":" + mouseY);

        hideCard.clear();

        if (onCheckCardClickEvent(mouseX, mouseY)) {
            update();
            return;
        }

        // Check left top of moving card
        EndPos = deckPositoinManager.getCardInfo(mouseX - mouseDx, mouseY - mouseDy);
        if (runCommand(EndPos)) {
            return;
        }

        // Check the mouse X,Y
        EndPos = deckPositoinManager.getCardInfo(mouseX, mouseY);
        if (runCommand(EndPos)) {
            return;
        }

        // Check Right top of moving card
        EndPos = deckPositoinManager.getCardInfo(mouseX + (100 - mouseDx), mouseY - mouseDy);
        if (runCommand(EndPos)) {
            return;
        }

        update();
    }

    private void onTouchMove(int mouseX, int mouseY) {
        if (isMovingCard) {
            currentMouseX = mouseX;
            currentMouseY = mouseY;
            update();
        }
    }

    private void makeHideCardList() {
        hideCard.clear();

        if (drawEngine != playDrawEngine) {
            return;
        }

        int deck = StartPos.deck;
        int moveCount = StartPos.position + 1;

        //WinLog.i(TAG, "paint " + Integer.toString(deck) + ":" + Integer.toString(moveCount));

        for (int i = 0; i < moveCount; i++) {
            Card card = solitare.getDeck(deck).get(i);
            if (card != null) {
                // WinLog.i(TAG, card.toString());
                int cardNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();
                hideCard.push(cardNumber);
                // WinLog.i(TAG, card.toString() + " : " + Integer.toString(imageNumber));
            } else {
                Log.i(LOG_TAG, "Card is null!");
            }
        }
    }


    private void loadConfig() {
        Log.d(LOG_TAG, "loadConfig()");
        SharedPreferences pref = mContext.getSharedPreferences("Solitaire", MODE_PRIVATE);
        int bgImage = pref.getInt("bgImage", 0);
        profile.setBG(bgImage);
    }

    private void saveConfig() {
        Log.d(LOG_TAG, "saveConfig()");

        SharedPreferences pref = mContext.getSharedPreferences("Solitaire", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        edit.putInt("bgImage", profile.getBG());
        edit.commit();
    }
}
