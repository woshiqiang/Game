package com.hbck.game;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * @author JianQiang Ding
 * @Date 2018-07-06.
 */
public class Face extends Sprite {
    int speed = 12;
    int tX;
    int tY;

    public Face(Bitmap defaultBitmap, Point position, Point touchPoint) {
        super(defaultBitmap, position);
        int X = touchPoint.x - position.x;
        int Y = touchPoint.y - position.y;

        int D = (int) Math.sqrt(X * X + Y * Y);

        tX = speed * X / D;
        tY = speed * Y / D;
    }

    public void move() {
        this.position.x += tX;
        this.position.y += tY;
    }
}
