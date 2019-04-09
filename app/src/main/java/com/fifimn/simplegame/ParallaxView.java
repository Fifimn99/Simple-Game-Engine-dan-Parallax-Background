package com.fifimn.simplegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class ParallaxView extends SurfaceView implements Runnable {

    ArrayList<Background> backgrounds;

    private volatile boolean running;
    private Thread gameThread = null;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    Context context;

    long fps = 60;
    int screenWidth;
    int screenHeight;

    public ParallaxView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        ourHolder = getHolder();
        paint = new Paint();

        backgrounds = new ArrayList<>();

        backgrounds.add(new Background(
                this.context,
                screenWidth,
                screenHeight,
                "background",  0, 90, 30));

        backgrounds.add(new Background(
                this.context,
                screenWidth,
                screenHeight,
                "grass",  0, 85, 200));
    }

    @Override
    public void run() {
        while (running) {
            long startFrameTime = System.currentTimeMillis();

            update();
            draw();

            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void update() {
        for (Background bg: backgrounds) {
            bg.update(fps);
        }
    }

    private void drawBackground(int position) {
        Background bg = backgrounds.get(position);

        Rect fromRect1 = new Rect(0, 0, bg.width - bg.xClip, bg.height);
        Rect toRect1 = new Rect(bg.xClip, bg.startY, bg.width, bg.endY);

        Rect fromRect2 = new Rect(bg.width - bg.xClip, 0, bg.width, bg.height);
        Rect toRect2 = new Rect(0, bg.startY, bg.xClip, bg.endY);

        if (!bg.reversedFirst) {
            canvas.drawBitmap(bg.bitmap, fromRect1, toRect1, paint);
            canvas.drawBitmap(bg.bitmapReversed, fromRect2, toRect2, paint);
        }
        else {
            canvas.drawBitmap(bg.bitmap, fromRect2, toRect2, paint);
            canvas.drawBitmap(bg.bitmapReversed, fromRect1, toRect1, paint);
        }

    }

    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 3, 70));

            drawBackground(0);
            drawBackground(1);

            paint.setTextSize(80);
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawText("Fifi Maghfirotun Nisa'", screenWidth / 3 + 100, screenHeight - 310, paint);

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e(ParallaxView.class.getSimpleName(), "Error: " + e);
        }
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
