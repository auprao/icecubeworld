package com.gaydevelopment;

public class BackGround extends GameObject{

    public BackGround(int x, int y) {
        super(x, y, "file:dirtBackgroundSprite.png");
        this.setScaleX(0.3);
        this.setScaleY(0.3);
    }
}