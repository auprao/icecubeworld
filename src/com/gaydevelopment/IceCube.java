package com.gaydevelopment;

import javafx.scene.image.Image;

import java.util.List;

public class IceCube extends GameObject {
    static final double fallSpeedCap = 30;
    static final double gravity = 2.5;
    public double melting = 100.0;

    public boolean canJump = true;

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
        melting -= 0.1;
        melt(melting);
    }

    public void collide(List<GameObject> collisionObjects) {
        for (GameObject obj: collisionObjects) {
            if (obj instanceof Grass) {
                this.velocityY = 0;
                this.canJump = true;
            }
            if (obj instanceof Fridge){
                melting = 100;
            }
            if (obj instanceof Snowflake){
                melting += melting *0.1;
                if (melting >100){
                    melting = 100;
                }
            }
        }
    }

    public void setImage(String path) {
        this.setImage(new Image(path));
    }

    public void melt(double melting){
        if (melting <= 100 && melting > 90){
            setImage("file:iceCubeSprite.png");
        }
        if (melting <= 90 && melting > 80){
            setImage("file:iceCube90.png");
        }
        if (melting <= 80 && melting > 70){
            setImage("file:iceCube80.png");
        }
        if (melting <= 70 && melting > 60){
            setImage("file:iceCube70.png");
        }
        if (melting <= 60 && melting > 50){
            setImage("file:iceCube60.png");
        }
        if (melting <= 50 && melting > 40){
            setImage("file:iceCube50.png");
        }
        if (melting <= 40 && melting > 30){
            setImage("file:iceCube40.png");
        }
        if (melting <= 30 && melting > 20){
            setImage("file:iceCube30.png");
        }
        if (melting <= 20 && melting > 10){
            setImage("file:iceCube20.png");
        }
        if (melting <= 10 && melting > 0){
            setImage("file:iceCube10.png");
        }
    }

}
