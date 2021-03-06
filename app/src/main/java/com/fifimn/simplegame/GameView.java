package com.fifimn.simplegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GameView extends SurfaceView implements Runnable {

    Thread gameThread = null;
    SurfaceHolder ourHolder;

    volatile boolean playing;

    Canvas canvas;
    Paint paint;

    long fps;

    private long timeThisFrame;

    Bitmap bitmapBob;

    boolean isMoving = false;
    float walkSpeedPerSecond = 150;
    float bobXPosition = 10;


    public GameView(Context context) {
        super(context);

        ourHolder = getHolder();
        paint =new Paint();

        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob);
    }

    @Override
    public void run() {
        while (playing){
            long startFrameTime = System.currentTimeMillis();

            update();
            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame > 0){
                fps = 1000 / timeThisFrame;
            }


        }

    }

    private void update() {
        if(isMoving){
            bobXPosition = bobXPosition + (walkSpeedPerSecond / fps);
        }
    }
    public void draw(){
        if (ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 26,128,182 ));
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(45);
            canvas.drawText("FPS:" + fps, 20, 40, paint);
            canvas.drawBitmap(bitmapBob, bobXPosition, 200, paint);
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
    public void pause(){
        playing = false;
        try{
            gameThread.join();
        }catch(InterruptedException e){
            Log.e("Error:", "joining thread");
        }
    }
    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){

            case MotionEvent.ACTION_DOWN:
                isMoving = true;
                break;

            case MotionEvent.ACTION_UP:
                isMoving = false;
                break;
        }
        return true;
    }
}
