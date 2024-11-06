package com.gaydevelopment;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.util.List;

public class IceCube extends GameObject {
    static final double fallSpeedCap = 30;
    static final double gravity = 2.5;
    public double solidness = 100.0;

    public boolean canJump = true;

    public IceCube(int x, int y, String filePath) {
        super(x, y, filePath);
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.velocityX);
        this.setY(this.getY() + this.velocityY);
        //System.out.println(this.getX());

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
        solidness -= 0.1;
        melt(solidness);

        if (this.getY() > Main.height){
            Main.mainTimeline.stop();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Game over");
            errorAlert.setContentText("You fell down! :(");
            errorAlert.show();
            errorAlert.setOnCloseRequest(event -> {
                    errorAlert.hide();
                }
            );
        }

    }

    public void collide(List<GameObject> collisionObjects) {
        for (GameObject obj: collisionObjects) {
            if (obj instanceof Air || obj instanceof BackGround || obj.equals(this)) continue;

            if (obj instanceof Fridge) {
                this.solidness = 100.0;
                continue;
            }

            if (obj instanceof Snowflake) {
                this.solidness += 33;
                if (this.solidness > 100) this.solidness = 100;
                Main.removeGameObject(obj);
                continue;
            }

            this.velocityY = 0;

            if ((obj instanceof Grass || obj instanceof SnowGrass) && (obj.getY() > this.getY())) {
                this.setY(this.getY() - 6);
            }

            else if (obj instanceof Grass || obj instanceof SnowGrass || obj instanceof Ground) {
                this.setY(this.getY() + 5);
            }

            if (this.velocityX < 0) {
                this.setX(this.getX() + 5);
            }

            else if (this.velocityX > 0) {
                this.setX(this.getX() - 5);
            }

            if (obj.getY() > this.getY()) {
                this.canJump = true;
            }
        }
    }

    /*
    public LinkedList<GameObject> checkCollisions() {
        LinkedList<GameObject> colliding = new LinkedList<>();
        for (GameObject obj: Main.gameObjects) {
            if (obj.equals(this)) continue;
            if (obj.getBoundsInParent().intersects(this.getBoundsInParent())) {
                colliding.add(obj);
            }
            if (obj instanceof Fridge){
                solidness = 100;
            }
            if (obj instanceof Snowflake){
                solidness += 30;
                if (solidness >100){
                    solidness = 100;
                }
            }
        }
        return colliding;
    }
    */

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
        if (melting <= 0){
            Main.mainTimeline.stop();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Game over");
            errorAlert.setContentText("You melted :(");
            errorAlert.show();
            errorAlert.setOnCloseRequest(event -> {
                errorAlert.hide();
            }
            );
        }
    }

}
