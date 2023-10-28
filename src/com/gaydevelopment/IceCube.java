package com.gaydevelopment;

import java.util.List;

public class IceCube extends GameObject {
    static final double fallSpeedCap = 30;
    static final double gravity = 2.5;

    public IceCube(int x, int y, String filePath) {
        super(x, y, filePath);
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.velocityX);
        this.setY(this.getY() + this.velocityY);
        if (this.velocityY < fallSpeedCap) {
            this.velocityY += gravity;
        }
    }

    public void collide(List<GameObject> collisionObjects) {
        for (GameObject obj: collisionObjects) {
            if (obj instanceof Ground) {
                this.velocityY = 0;
            }
        }
    }

}
