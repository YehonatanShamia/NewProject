package com.example.project;

import android.graphics.Bitmap;

public class shuriken extends Element {

    public static final int RIGHT=1;
    public static final int LEFT=-1;

    private int direction;

    public shuriken(int x, int y, int width, int height, Bitmap bitmap, int direction) {
        super(x, y, width, height, bitmap);
        this.direction = direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    public int getDirection() {
        return direction;
    }

    public void shurikenRight(){
        direction = RIGHT;
    }
    public void shurikenLeft(){
        direction = LEFT;
    }

    public void update(){
        if((this.direction > 0 && this.x < this.width - GameView.frameWidthNinja)
                || (this.direction < 0 && this.x >0 ) ||
                (this.direction==0))
            this.x += direction*10;
    }
}

/*

package com.example.Project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GameView extends SurfaceView implements Runnable {

    private Thread gameThread;
    private SurfaceHolder ourHolder;
    private volatile boolean playing;
    private Canvas canvas;
    private Bitmap bitmapRunningMan;
    private boolean isMoving;
    private float runSpeedPerSecond = 500;
    private float manXPos = 10, manYPos = 10;
    private int frameWidth = 69, frameHeight = 274;
    private int frameCount = 9;
    private int currentFrame = 0;
    private long fps;
    private long timeThisFrame;
    private long lastFrameChangeTime = 0;
    private int frameLengthInMillisecond = 50;
    private Rect frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
    private RectF whereToDraw = new RectF(manXPos, manYPos, manXPos + frameWidth, frameHeight);


    public GameView(Context context){

        super(context);
        ourHolder = getHolder();
        bitmapRunningMan = BitmapFactory.decodeResource(getResources(), R.drawable.shurikan1);
        bitmapRunningMan = Bitmap.createScaledBitmap(bitmapRunningMan, frameWidth * frameCount, frameHeight, false);
    }
    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }
    public void update() {
        if (isMoving) {
            manXPos = manXPos + runSpeedPerSecond / fps;
            if (manXPos > getWidth()) {
                manYPos += (int) frameHeight;
                manXPos = 10;
            }
            if (manYPos + frameHeight > getHeight()) {
                manYPos = 10;
            }
        }
    }
    public void manageCurrentFrame() {
        long time = System.currentTimeMillis();
        if (isMoving) {
            if (time > lastFrameChangeTime + frameLengthInMillisecond) {
                lastFrameChangeTime = time;
                currentFrame++;
                if (currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
    }
    public void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            whereToDraw.set((int) manXPos, (int) manYPos, (int) manXPos + frameWidth, (int) manYPos + frameHeight);
            manageCurrentFrame();
            canvas.drawBitmap(bitmapRunningMan, frameToDraw, whereToDraw, null);
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch(InterruptedException e) {
            Log.e("ERR", "Joining Thread");
        }
    }
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                isMoving = !isMoving;
                break;
        }
        return true;
    }
}


 */
