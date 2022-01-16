package com.example.project;

public class ninja {
    private int x;
    private int y;

    public ninja(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void playerLeft(){
        x -= 10;
    }
    public void playerRight(){
        x += 10;
    }
    public void playerJump(){
        y += 20;
    }
}
