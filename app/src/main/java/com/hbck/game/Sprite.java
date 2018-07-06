package com.hbck.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * 基类 精灵
 *
 * @author JianQiang Ding
 * @Date 2018-07-06.
 */
public abstract class Sprite {
    private Bitmap defaultBitmap;
    protected Point position;

    public Sprite(Bitmap defaultBitmap, Point position) {
        this.defaultBitmap = defaultBitmap;
        this.position = position;
    }

    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(defaultBitmap, position.x, position.y, null);
    }

}
