package com.gaydevelopment;

import java.util.LinkedList;
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

        /*LinkedList<GameObject> coll = this.checkCollisions();
        for (GameObject collisionObject: coll) {
            if (!(collisionObject instanceof Air)) {
                this.setX(this.getX() - (this.velocityX));
                this.setY(this.getY() + (this.getY() - collisionObject.getY()));
            }
        }*/

        if (this.velocityY < fallSpeedCap) {
            this.velocityY += gravity;
        }
        melting -= 0.1;
        melt(melting);
    }

    public void collide(List<GameObject> collisionObjects) {
        for (GameObject obj: collisionObjects) {
            if (obj instanceof Air || obj.equals(this)) continue;

            if (obj instanceof Fridge) {
                this.melting = 100.0;
                continue;
            }

            this.velocityY = 0;
            if (obj instanceof Grass) {
                this.setY(this.getY() - 5);
            }

            if (obj instanceof Ground) {
                this.setY(this.getY() + 5);
            }

            if (this.velocityX < 0) {
                this.setX(this.getX() + 5);
            }

            if (this.velocityX > 0) {
                this.setX(this.getX() - 5);
            }



            this.canJump = true;
        }
    }

    public LinkedList<GameObject> checkCollisions() {
        LinkedList<GameObject> colliding = new LinkedList<>();
        for (GameObject obj: Main.gameObjects) {
            if (obj.equals(this)) continue;
            if (obj.getBoundsInParent().intersects(this.getBoundsInParent())) {
                colliding.add(obj);
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
        return colliding;
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
