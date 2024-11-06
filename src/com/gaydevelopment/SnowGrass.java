package com.gaydevelopment;

public class SnowGrass extends GameObject{

    public SnowGrass(int x, int y) {
        super(x, y, "file:snowGroundSprite.png");
        this.setScaleX(0.3);
        this.setScaleY(0.3);
    }
}
