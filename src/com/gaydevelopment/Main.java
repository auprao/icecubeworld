package com.gaydevelopment;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

import static com.gaydevelopment.Tiles.*;

public class Main extends Application {

    static int width = 1800;
    static int height = 620;

    static final int FPS = 60;

    static IceCube player;

    static List<Rectangle> rainParticles = new LinkedList<>();

    static LinkedList<GameObject> gameObjects = new LinkedList<>();

    static AnchorPane root = new AnchorPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);

        Rectangle bg = new Rectangle(width,height);
        bg.setFill(Color.rgb(0, 0, 40));
        root.getChildren().add(bg);

        for (int i = 0; i < 100; i++) {
            Rectangle particle = new Rectangle(5, 10);
            particle.setFill(Color.rgb(204, 255, 255, 0.5));
            particle.setX(Math.random() * width);
            particle.setY(Math.random() * -height);
            rainParticles.add(particle);
            root.getChildren().add(particle);
        }

        Tiles[][] map1 = {
                {Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air, Grass, Grass,Air,Air,Air, Air ,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Grass, Ground, Ground, Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Ground, Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air, Ground, Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Grass,Grass,Grass,Air,Air,Air,Air,Air,Air,Air,Air,Air, Grass,Grass,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air, Air ,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Grass, Ground,Air,Air,Air,Air,Air,Air,Snowflake,Air,Grass,Grass,Air,Air,Air,Air,Ground,Ground,Ground,Grass,Air,Air,Air,Air,Air,Air,Air,Air,Ground,Ground,Air,Air,Air,Air,Air,Air,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Ground,Ground,Grass,Air,Air,Air,Air,Grass,Grass,Grass,Ground,Ground,Grass,Air,Air,Air,Air,Air,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Fridge,Air,Grass,Ground,Ground,Ground,Air, Air, Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Ground,Ground,Ground, Grass, Grass, Grass, Grass,Ground,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Grass,Grass,Grass,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Air,Air,Air,Grass,Grass,Air,Air,Air,Air,Air,Air,Grass,Grass,Air,Air,Air,Air,Grass,Grass,Grass,Grass,Grass,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air, Air, Ground, Ground, Ground,Ground,Ground,Ground,Air,Air,Air,Grass,Grass,Ground,Ground,Grass,Air,Air,Air,Air,Grass,Ground,Ground,Air,Air,Air,Grass,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air}
        };

        loadMapFromArray(map1);

        Timeline mainTimeline = new Timeline();
        mainTimeline.setCycleCount(-1);
        mainTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1 / (double) FPS), e -> newFrame()));
        mainTimeline.play();
        //temp

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT ) {
                player.velocityX = -10;
            }
            if (key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) {
                player.velocityX = 10;
            }
            if (key.getCode() == KeyCode.SPACE || key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) {
                if (player.canJump) {
                    player.velocityY = -30;
                    player.canJump = false;
                }
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if (key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT) {
                player.velocityX = 0;
            }
            if (key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) {
                player.velocityX = 0;
            }
        });

        primaryStage.show();
    }

    public void rain(){
        double newX;
        double newY;
        for (int i = 0; i < rainParticles.size(); i++) {
            newX = rainParticles.get(i).getX() + (Math.random() - 0.5);
            newY = rainParticles.get(i).getY() + (Math.random() + 10);
            rainParticles.get(i).setX(newX);
            rainParticles.get(i).setY((newY + height) % height);
        }
    }

    // TODO: 28.10.2023 manually create arrays / maps

    public void loadMapFromArray(Tiles[][] array){
        IceCube icecube = new IceCube(200, 200, "file:iceCubeSprite.png");
        icecube.setScaleX(0.3);
        icecube.setScaleY(0.3);
        addGameObject(icecube);
        player = icecube;

        double generationX = 0;
        double generationY = 0;
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[0].length; c++) {
                switch (array[r][c]) {
                    case Air:
                        addGameObject(new Air((int) generationX, (int) generationY));
                        break;
                    case Ground:
                        addGameObject(new Ground((int) generationX, (int) generationY));
                        break;
                    case Grass:
                        addGameObject(new Grass((int) generationX, (int) generationY));
                        break;
                    case Fridge:
                        addGameObject(new Fridge((int) generationX, (int) generationY));
                        break;
                    case Snowflake:
                        addGameObject(new Snowflake((int) generationX, (int) generationY));
                        break;
                    case SnowGround:
                        addGameObject(new Snow((int) generationX, (int) generationY));
                        break;
                }
                generationX = c * (0.3 * 200);
            }
            generationY = (r) * (height / array.length);
        }
    }

    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
        root.getChildren().add(obj);
    }

    public void removeGameObject(GameObject obj) {
        gameObjects.remove(obj);
        root.getChildren().remove(obj);
    }

    public void newFrame() {
        LinkedList<GameObject> playerCollisions = new LinkedList<>();
        for (GameObject obj: gameObjects) {
            if (obj.getBoundsInParent().intersects(player.getBoundsInParent())) {
                playerCollisions.add(obj);
            }
            obj.update();
        }
        player.collide(playerCollisions);
        rain();

        if (player.getX() > width - 10) {
            for (GameObject obj: gameObjects) {
                obj.setX(obj.getX() - width);
            }
        }
        else if (player.getX() < 10) {
            for (GameObject obj: gameObjects) {
                obj.setX(obj.getX() + width);
            }
        }
    }
}
