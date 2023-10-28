package com.gaydevelopment;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObject extends ImageView {
    public double velocityX, velocityY;

    public GameObject(int x, int y, String filePath) {
        super(new Image(filePath));
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void update() {
        this.setX(this.getX() + this.velocityX);
        this.setY(this.getY() + this.velocityY);
    }


}
