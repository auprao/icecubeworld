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

import static com.gaydevelopment.Tiles.Air;
import static com.gaydevelopment.Tiles.Ground;

public class Main extends Application {

    static int width = 1280;
    static int height = 720;

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
                {Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air,Air,Air,Air,Air,Air,Air,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Ground, Ground, Ground, Ground,Ground,Ground,Ground,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air,Air,Air,Air,Air,Air,Air,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Ground, Ground, Ground, Ground,Ground,Ground,Ground,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Ground,Ground,Ground,Ground,Ground},
                {Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air}
        };

        loadMapFromArray(map1);

        Timeline mainTimeline = new Timeline();
        mainTimeline.setCycleCount(-1);
        mainTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1 / (double) FPS), e -> newFrame()));
        mainTimeline.play();
        //temp
        IceCube icecube = new IceCube(500, 100, "file:iceCubeSprite.png");
        icecube.setScaleX(0.3);
        icecube.setScaleY(0.3);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.A) {
                player.velocityX = -10;
            }
            if (key.getCode() == KeyCode.D) {
                player.velocityX = 10;
            }
            if (key.getCode() == KeyCode.SPACE) {
                player.velocityY = -30;
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if (key.getCode() == KeyCode.A) {
                player.velocityX = 0;
            }
            if (key.getCode() == KeyCode.D) {
                player.velocityX = 0;
            }
        });
        addGameObject(icecube);

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
        IceCube icecube = new IceCube(500, 100, "file:iceCubeSprite.png");
        icecube.setScaleX(0.3);
        icecube.setScaleY(0.3);
        addGameObject(icecube);
        player = icecube;

        int generationX = 0;
        int generationY = 0;
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[0].length; c++) {
                switch (array[r][c]) {
                    case Air:
                        addGameObject(new Air(generationX, generationY));
                        break;
                    case Ground:
                        addGameObject(new Ground(generationX, generationY));
                        break;
                }
                generationX = c * (width / array[0].length) - 50;
            }
            generationY = (r) * (height / array.length);
        }
    }

    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
        root.getChildren().add(obj);
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
    }



}
