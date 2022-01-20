package com.example.project;

import android.graphics.Bitmap;

import java.util.Random;

public class Tnt extends Element {


    public static final int SLOW_SPEED = 500;
    private int speed;

    public boolean isTntAlive() {
        return tntAlive;
    }

    public void setTntAlive(boolean tntAlive) {
        this.tntAlive = tntAlive;
    }

    private boolean tntAlive = true;



    public Tnt(int x, int y, int width, int height,Bitmap bitmap) {
        super(x, y, width, height, bitmap);
        randomizeX();
    }

    private void randomizeX() {
        Random rnd = new Random();
        this.x = rnd.nextInt(GameSettings.screeenWidth);
    }

    public void update() {
        if (y <= GameSettings.screeenHeight)
            this.y += 10;
        }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void resetAfterCollision(){
        this.setY(0);
        randomizeX();
    }


    public void startTntThread()
    {
        // this is the thread in charge of a specific tnt object
        // decrease Y until wee reach bottom screen
        //  once touches ground:
        // 1) randomize X
        // 2) set Y to 0
        // continue

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (tntAlive) {
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (y >= GameSettings.screeenHeight) {
                        setY(0);
                        randomizeX();
                    }
                }

            }

        }).start();
    }

}

