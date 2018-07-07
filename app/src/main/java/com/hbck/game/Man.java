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
    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    private int speed = 6;

    public Man(Bitmap defaultBitmap, Point position) {
        super(defaultBitmap, position);
    }

    public Face createFace(Context context, Point touchPoint) {
        //加载笑脸图片
//        Bitmap faceBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.rating_small);
//        Bitmap faceBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a05);
//        Bitmap faceBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_72);
        Bitmap faceBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a8n);
        Face face = new Face(faceBitmap, new Point(position.x + 100, position.y + 100), touchPoint);//创建笑脸
        return face;
    }

    /**
     * 移动小人
     *
     * @param direction 方向
     */
    public void move(int direction) {
        if (direction == DOWN) {
            this.position.y += speed;
        } else if (direction == UP) {
            this.position.y -= speed;
        } else if (direction == LEFT) {
            this.position.x -= speed;
        } else if (direction == RIGHT) {
            this.position.x += speed;
        }
    }
}
