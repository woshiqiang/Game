package com.hbck.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * @author JianQiang Ding
 * @Date 2018-07-06.
 */
public class Man extends Sprite {

    public Man(Bitmap defaultBitmap, Point position) {
        super(defaultBitmap, position);
    }

    public Face createFace(Context context, Point touchPoint) {
        Bitmap faceBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.rating_small);
        Face face = new Face(faceBitmap, new Point(position.x + 100, position.y + 100), touchPoint);
        return face;
    }
}
