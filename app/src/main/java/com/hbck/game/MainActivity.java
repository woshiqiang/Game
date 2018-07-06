package com.hbck.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    private GameUI gameUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameUI = new GameUI(this);
        setContentView(gameUI);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameUI.handleTouce(event);
        return super.onTouchEvent(event);
    }
}
