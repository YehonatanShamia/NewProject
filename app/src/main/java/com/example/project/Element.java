package com.example.project;

import android.graphics.Bitmap;

public abstract class Element
{
    protected int x;
    protected int y;
    protected int width,height;
    protected Bitmap bitmap;

    public Element(int x, int y, int width, int height, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
