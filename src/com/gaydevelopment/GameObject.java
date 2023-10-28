package com.gaydevelopment;

import javafx.scene.image.Image;

public class GameObject {
    public int x, y;
    public int width, height;

    public double velocityX, velocityY;

    public Image sprite;

    public GameObject(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;

        this.sprite = sprite;
    }

    public void update() {
        this.x += this.velocityX;
        this.y += this.velocityY;
    }


}
