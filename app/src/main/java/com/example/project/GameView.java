package com.example.project;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

class GameView extends SurfaceView implements Runnable {

    private Thread gameThread;
    private SurfaceHolder ourHolder;
    private volatile boolean playing;
    private Canvas canvas;
    private Bitmap bitmapBackground;


    private long fps;
    private float runSpeedPerSecond = 5000;
    private long timeThisFrame;
    private long lastFrameChangeTime = 0;
    private int frameLengthInMillisecond = 100;


    private Bitmap bitmapNinjaLeft;
    private Bitmap bitmapNinjaRight;
 //   private Bitmap bitmapNinjaStill;
    private boolean isMovingNinja;
    public static int frameWidthNinja = 185, frameHeightNinja = 165;
   // public static int frameWidthNinjaStill = 70;
    private int frameCountNinja = 6;
  //  private int frameCountNinjaStill = 4;
    private int currentFrameNinja = 0;
    boolean firstTimeNinja = true;
    private Rect frameToDrawNinja = new Rect(0, 0, frameWidthNinja, frameHeightNinja);
    private RectF whereToDrawNinja = new RectF(0, 0, frameWidthNinja, frameHeightNinja);
    private ninja ninja;
    private Bitmap bitmapToDraw = bitmapNinjaRight;

    private long timer = 0;
    private int damage = 0;



    private shuriken shuriken;
    private Bitmap bitmapShuriken;
    private boolean isMovingShuriken = true ;
    public static int frameWidthShuriken = 69, frameHeightShuriken = 274;
    private int frameCountShuriken = 9;
    private int currentFrameShuriken = 0;
    boolean firstTimeShuriken = true;
    private Rect frameToDrawShuriken = new Rect(0, 0, frameWidthShuriken, frameHeightShuriken);
    private RectF whereToDrawShuriken = new RectF(0, 0, frameWidthShuriken, frameHeightShuriken);


    private static final int MAX_NUM_OF_TNT = 4;
    private boolean isFirstTimeTnt = false;
    private ArrayList<Tnt> tntArry = new ArrayList<>();
    private Bitmap bitmapTnt;
    public static int frameWidthTnt = 75, frameHeightTnt = 159;
    private Rect frameToDrawTnt = new Rect(0, 0, frameWidthTnt, frameHeightTnt);
    private RectF whereToDrawTnt = new RectF(0, 0, frameWidthTnt, frameHeightTnt);
    private int currentFrameTnt = 2;
    private int frameCountTnt = 2;



    public GameView(Context context){
        super(context);
        ourHolder = getHolder();

        bitmapNinjaLeft = BitmapFactory.decodeResource(getResources(), R.drawable.ninja_left);
        bitmapNinjaLeft = Bitmap.createScaledBitmap(bitmapNinjaLeft, frameWidthNinja * frameCountNinja, frameHeightNinja, false);

        bitmapNinjaRight = BitmapFactory.decodeResource(getResources(), R.drawable.ninja_right);
        bitmapNinjaRight = Bitmap.createScaledBitmap(bitmapNinjaRight, frameWidthNinja * frameCountNinja, frameHeightNinja, false);
/*
        bitmapNinjaStill = BitmapFactory.decodeResource(getResources(), R.drawable.ninja_still);
        bitmapNinjaStill = Bitmap.createScaledBitmap(bitmapNinjaStill, frameWidthNinjaStill * frameCountNinjaStill, frameHeightNinja, false);
*/
        bitmapShuriken = BitmapFactory.decodeResource(getResources(), R.drawable.shurikan1);
        bitmapShuriken = Bitmap.createScaledBitmap(bitmapShuriken, frameWidthShuriken * frameCountShuriken, frameHeightShuriken, false);

        bitmapTnt = BitmapFactory.decodeResource(getResources(), R.drawable.tnt);
        bitmapTnt = Bitmap.createScaledBitmap(bitmapTnt, frameWidthTnt * currentFrameTnt, frameHeightTnt, false );


    }
    @Override
    public void run() {
            // init game
        runTimerThread();

        while (playing) {

            long startFrameTime = System.currentTimeMillis();
            draw();
            update();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 50 / timeThisFrame;
            }
        }
    }

    private void runTimerThread() {
        // timer thread
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                timer = 0;

                while(playing)
                {
                    try {
                        Thread.currentThread().sleep(1000);
                        timer++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void update() {

        if(ninja !=null && isMovingNinja) {
            ninja.update();
        }

        for (Tnt tnt : tntArry)
            tnt.update();

        if(timer == 5 && tntArry.size() ==0)
        {
            {   Tnt tnt = new Tnt(0, 0, frameHeightTnt, frameWidthTnt, bitmapTnt);
                tntArry.add(tnt);
                tnt.setSpeed(Tnt.SLOW_SPEED);
                tnt.startTntThread();
            }
         }

        if (tntHitNinja())
        {
            tntArry.get(0).setTntAlive(false);
        }
    }




    public void manageCurrentFrame() {
        long time = System.currentTimeMillis();

        if (isMovingNinja) {
            if (time > lastFrameChangeTime + frameLengthInMillisecond) {
                lastFrameChangeTime = time;
                currentFrameNinja++;
                currentFrameTnt++;

                if (currentFrameTnt >= frameCountTnt)
                    currentFrameTnt = 0;

                if (currentFrameNinja >= frameCountNinja) {
                    currentFrameNinja = 0;
                }
                /*
                if (currentFrameNinja >= frameCountNinjaStill) {
                    currentFrameNinja = 0;
                }*/
            }
            frameToDrawNinja.left = currentFrameNinja * frameWidthNinja;
            frameToDrawNinja.right = frameToDrawNinja.left + frameWidthNinja;

            frameToDrawTnt.left = currentFrameTnt * frameWidthTnt;
            frameToDrawTnt.right = frameToDrawTnt.left + frameWidthTnt;
        }
    }

    public void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            GameSettings.screeenWidth = canvas.getWidth();
            GameSettings.screeenHeight = canvas.getHeight();

            bitmapBackground = BitmapFactory.decodeResource(getResources(), R.drawable.background_ninja_game);
            bitmapBackground = Bitmap.createScaledBitmap(bitmapBackground, GameSettings.screeenWidth, GameSettings.screeenHeight, true);
            canvas.drawBitmap(bitmapBackground, 0, 0, null);

            if(firstTimeNinja)
            {
                ninja = new ninja(canvas.getWidth()/2 - frameWidthNinja,(-1*(frameHeightNinja -canvas.getHeight()))-130,frameWidthNinja,frameHeightNinja, bitmapToDraw);
                firstTimeNinja =false;
                GameSettings.screeenWidth = canvas.getWidth();
                GameSettings.screeenHeight = canvas.getHeight();

            }

            whereToDrawNinja.set((int) ninja.getX(), ninja.getY(), ninja.getX()+ frameWidthNinja, ninja.getY() + frameHeightNinja);
            manageCurrentFrame();

            if(ninja.getDirection() == com.example.project.ninja.LEFT)
            {
                frameCountNinja = 6;
                bitmapToDraw = bitmapNinjaLeft;
            }
            if(ninja.getDirection() == com.example.project.ninja.RIGHT)
            {
                frameCountNinja = 6;
                bitmapToDraw = bitmapNinjaRight;
            }
            /*
            if(ninja.getDirection() == com.example.project.ninja.STILL)
            {
                frameCountNinja = 4;
                bitmapToDraw = bitmapNinjaStill;
            }*/
            canvas.drawBitmap(bitmapToDraw, frameToDrawNinja, whereToDrawNinja, null);


            for (Tnt tnt : tntArry) {
                whereToDrawTnt.set(tnt.getX(),tnt.getY(), tnt.getX() + frameWidthTnt, tnt.getY() + frameHeightTnt);
                canvas.drawBitmap(tnt.getBitmap(), frameToDrawTnt, whereToDrawTnt, null);
            }

            ourHolder.unlockCanvasAndPost(canvas);
/*
            if (firstTimeShuriken){
                int direction = -1;
                double res = Math.random();
                if(res >= 0.5)
                {
                    direction = 1;
                    shuriken = new shuriken(canvas.getWidth(),50 ,direction*(frameHeightShuriken-canvas.getHeight()), canvas.getWidth(),canvas.getHeight());
                    isMovingShuriken = true;
                }
                if(res < 0.5)
                {
                    direction = -1;
                    shuriken = new shuriken(0, 50,direction*(frameHeightShuriken-canvas.getHeight()), canvas.getWidth(),canvas.getHeight());
                    isMovingShuriken = true;
               }
            }*/
            // draw shuriken:
            // whereToDrawShuriken.set((int) shuriken.getX(), shuriken.getY(), shuriken.getX() + frameWidthShuriken, shuriken.getY() + frameHeightShuriken);
            // manageCurrentFrame();
            // canvas.drawBitmap(bitmapShuriken, frameToDrawShuriken, whereToDrawShuriken, null);
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

        if(event.getAction() == MotionEvent.ACTION_DOWN) {

            float x = (int) event.getX();
            float touchArea = getWidth() / 20;

            if (x < touchArea) // left!
            {
                    isMovingNinja = true;
                    ninja.ninjaLeft();


            } else if (x > getWidth() - touchArea) // right!
            {
                    ninja.ninjaRight();
                isMovingNinja = true;
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            ninja.ninjaStill();
            isMovingNinja = false;
        }
        return true;
    }

    public boolean tntHitNinja(){

        for (Tnt tnt : tntArry)
        {
            // check right corener
            // check left corener

            //if (ninja.getX() > tnt.getX() && ninja.getX() < tnt.getX() + tnt.getWidth() && ninja.getY() > tnt.getY() && tnt.getY() < )


            if (ninja.getX() >= tnt.getX() && ninja.getX() <= tnt.getX() + tnt.getWidth() && ninja.getY() >= ninja.getY() && ninja.getY() <= tnt.getY() + tnt.getHeight())
                return true;

            if (ninja.getX() + ninja.getWidth() >= tnt.getX() && ninja.getX() + ninja.getWidth() <= tnt.getX() + tnt.getWidth() && ninja.getY() + ninja.getHeight() >= ninja.getY() && ninja.getY() + ninja.getHeight() <= tnt.getY() + tnt.getHeight())
                return true;


        }
        return false;
    }

}


