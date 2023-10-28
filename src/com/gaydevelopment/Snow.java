package com.gaydevelopment;

public class Snow extends GameObject{

    public Snow(int x, int y) {
        super(x, y, "file:snowGroundSprite.png");
        this.setScaleX(0.3);
        this.setScaleY(0.3);
    }
}
