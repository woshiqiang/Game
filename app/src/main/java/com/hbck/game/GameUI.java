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
    private Man man;
    private List<Face> faces;

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

        man.drawSelf(canvas);

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
        faces = new CopyOnWriteArrayList<>();
        Bitmap manBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.avatar_boy);
        man = new Man(manBitmap, new Point(0, 0));

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
                Face face = man.createFace(getContext(), new Point(X, Y));
                faces.add(face);
                break;
        }
    }
}
