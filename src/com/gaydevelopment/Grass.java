package com.gaydevelopment;

public class Grass extends GameObject{

    public Grass(int x, int y) {
        super(x, y, "file:grassGroundSprite.png");
        this.setScaleX(0.3);
        this.setScaleY(0.3);
    }
}
