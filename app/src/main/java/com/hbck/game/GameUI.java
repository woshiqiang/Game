package com.hbck.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author JianQiang Ding
 * @Date 2018-07-06.
 */
public class GameUI extends SurfaceView implements SurfaceHolder.Callback {
    private RenderThread renderThread;
    private boolean flag;//线程运行的标记
    private SurfaceHolder holder;
    //-----------
    private Man man;//小人
    private List<Face> faces;//笑脸
    private MyButton upButton, downButton, leftButton, rightButton;//用到的按钮


    public GameUI(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }


    private class RenderThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (flag) {
                try {
                    drawUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void drawUI() {
        //锁定画布
        Canvas canvas = holder.lockCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        //绘制 文字
        Paint textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(100);
        canvas.drawText("双击666", 150, getHeight() / 3*2, textPaint);

        man.drawSelf(canvas);//绘制男孩

        downButton.drawSelf(canvas);//绘制按钮
        upButton.drawSelf(canvas);
        leftButton.drawSelf(canvas);
        rightButton.drawSelf(canvas);

        for (Face f : faces) {
            f.drawSelf(canvas);
            f.move();
            if (f.position.x < 0 || f.position.x > getWidth() || f.position.y < 0 || f.position.y > getHeight()) {
                faces.remove(f);
            }
        }

        holder.unlockCanvasAndPost(canvas);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        Bitmap manBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_boy);
        Bitmap manBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        man = new Man(manBitmap, new Point(getWidth() / 2 - manBitmap.getWidth() / 2, getHeight() / 4));//创建男孩
        faces = new CopyOnWriteArrayList<>();//可以在遍历的过程中进行增删
        //下
        Bitmap downBtnDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bottom_default);
        Bitmap downBtnPressedBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bottom_press);
        downButton = new MyButton(downBtnDefaultBitmap, new Point(getWidth() / 2 - downBtnDefaultBitmap.getWidth() / 2, getHeight() - 200), downBtnPressedBitmap);//创建button
        downButton.setOnClickListener(new MyButton.OnClickListener() {
            @Override
            public void onClick() {
                man.move(Man.DOWN);
            }
        });
        //上
        Bitmap upBtnDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.top_default);
        Bitmap upBtnPressedBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.top_press);
        upButton = new MyButton(upBtnDefaultBitmap, new Point(getWidth() / 2 - upBtnDefaultBitmap.getWidth() / 2, getHeight() - 400), upBtnPressedBitmap);//创建button
        upButton.setOnClickListener(new MyButton.OnClickListener() {
            @Override
            public void onClick() {
                man.move(Man.UP);
            }
        });
        //左
        Bitmap leftBtnDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.left_default);
        Bitmap leftBtnPressedBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.left_press);
        leftButton = new MyButton(leftBtnDefaultBitmap, new Point(getWidth() / 4 - leftBtnDefaultBitmap.getWidth() / 2, getHeight() - 300), leftBtnPressedBitmap);//创建button
        leftButton.setOnClickListener(new MyButton.OnClickListener() {
            @Override
            public void onClick() {
                man.move(Man.LEFT);
            }
        });

        //右
        Bitmap rightBtnDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.right_default);
        Bitmap rightBtnPressedBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.right_press);
        rightButton = new MyButton(rightBtnDefaultBitmap, new Point(getWidth() / 4 * 3 - rightBtnDefaultBitmap.getWidth() / 2, getHeight() - 300), rightBtnPressedBitmap);//创建button
        rightButton.setOnClickListener(new MyButton.OnClickListener() {
            @Override
            public void onClick() {
                man.move(Man.RIGHT);
            }
        });

        renderThread = new RenderThread();
        flag = true;
        renderThread.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }

    /**
     * 处理点击事件
     *
     * @param event
     */
    public void handleTouce(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN://当按下时创建笑脸
                int X = (int) event.getRawX();
                int Y = (int) event.getRawY();
                if (downButton.isClick(new Point(X, Y))) {
                    downButton.click();
                } else if (upButton.isClick(new Point(X, Y))) {
                    upButton.click();
                } else if (leftButton.isClick(new Point(X, Y))) {
                    leftButton.click();
                } else if (rightButton.isClick(new Point(X, Y))) {
                    rightButton.click();
                } else {
                    Face face = man.createFace(getContext(), new Point(X, Y));
                    faces.add(face);
                }

                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时 让按钮点击的状态改为false
                upButton.setIsClick(false);
                downButton.setIsClick(false);
                leftButton.setIsClick(false);
                rightButton.setIsClick(false);
                break;
        }
    }
}
