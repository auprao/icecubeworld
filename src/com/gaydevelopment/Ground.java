package com.gaydevelopment;

public class Ground extends GameObject{

    public Ground(int x, int y) {
        super(x, y, "file:trump.jpg");
        this.setScaleX(0.3);
        this.setScaleY(0.3);
    }
}
