package com.gaydevelopment;

public class Fridge extends GameObject {

    public Fridge(int x, int y) {
        super(x, y, "file:fridgeSprite.png");
        this.setScaleX(0.3);
        this.setScaleY(0.3);
    }
}
