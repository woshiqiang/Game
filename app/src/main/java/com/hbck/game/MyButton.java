package com.hbck.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * @author JianQiang Ding
 * @Date 2018-07-07.
 */
public class MyButton extends Sprite {
    private boolean isClick;
    private Bitmap pressedBitmap;
    private OnClickListener listener;

    public interface OnClickListener {
        void onClick();
    }

    //设置点击事件
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    //当按钮被点击时调用
    public void click() {
        if (listener != null) {
            listener.onClick();
        }
    }

    public MyButton(Bitmap defaultBitmap, Point position, Bitmap pressedBitmap) {
        super(defaultBitmap, position);
        this.pressedBitmap = pressedBitmap;
    }


    //修改自身
    @Override
    public void drawSelf(Canvas canvas) {
        if (isClick) {
            //绘制按下的图片
            canvas.drawBitmap(pressedBitmap, position.x, position.y, null);
        } else {
            super.drawSelf(canvas);//绘制默认图片
        }

    }

    public void setIsClick(boolean isClick) {
        this.isClick = isClick;
    }

    /**
     * 判断该点 是否点中了当前按钮
     *
     * @param touchPoint
     * @return
     */
    public boolean isClick(Point touchPoint) {
        //创建当前按钮的矩形区域
        Rect rect = new Rect(position.x, position.y, position.x + pressedBitmap.getWidth(), position.y + pressedBitmap.getHeight());
        //如果包含这个点，认为被点击了
        isClick = rect.contains(touchPoint.x, touchPoint.y);
        return isClick;
    }
}
