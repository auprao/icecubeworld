package com.gaydevelopment;

public class Snowflake extends GameObject {

    public Snowflake(int x, int y) {
        super(x, y, "file:snowflakeHelperSprite.png");
        this.setScaleX(0.3);
        this.setScaleY(0.3);
    }
}
