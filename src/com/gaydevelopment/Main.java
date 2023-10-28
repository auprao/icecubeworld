package com.gaydevelopment;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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

public class Main extends Application {

    static int width = 1280;
    static int height = 720;

    static final int FPS = 60;

    static List<Rectangle> rainParticles;

    static LinkedList<GameObject> gameObjects = new LinkedList<>();

    static AnchorPane root = new AnchorPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);

        Timeline mainTimeline = new Timeline();
        mainTimeline.setCycleCount(-1);
        mainTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1 / (double) FPS), e -> newFrame()));
        mainTimeline.play();
        //temp
        IceCube icecube = new IceCube(500, 500, "file:trump.jpg");

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.A) {
                icecube.velocityX = -10;
            }
            if (key.getCode() == KeyCode.D) {
                icecube.velocityX = 10;
            }
            if (key.getCode() == KeyCode.SPACE) {
                icecube.velocityY = -10;
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if (key.getCode() == KeyCode.A) {
                icecube.velocityX = 0;
            }
            if (key.getCode() == KeyCode.D) {
                icecube.velocityX = 0;
            }
        });
        addGameObject(icecube);

        primaryStage.show();

        for (int i = 0; i < 100; i++) {
            Rectangle particle = new Rectangle();
            particle.setFill(Color.rgb(204, 255, 255, 0.5));
            rainParticles.add(particle);
            root.getChildren().add(particle);
        }
    }

    public void rain(){
        double newX;
        double newY;
        for (int i = 0; i < rainParticles.size(); i++) {
            newX = rainParticles.get(i).getX() + (Math.random() - 0.5);
            newY = rainParticles.get(i).getX() + (Math.random() - 2);
            rainParticles.get(i).setX(newX);
            rainParticles.get(i).setY(newY);
        }
    }

    // TODO: 28.10.2023 manually create arrays / maps

    public void loadMapFromArray(GameObject[][] array){
        int generationX = 0;
        int generationY = 0;
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[0].length; c++) {
                gameObjects.add(array[r][c]);
                generationX = generationX + width / array.length;
            }
            generationY = generationY + height / array[0].length;
        }
        for (int i = 0; i < gameObjects.size(); i++) {
            root.getChildren().add(gameObjects.get(i));
        }
    }

    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
        root.getChildren().add(obj);
    }

    public void newFrame() {
        for (GameObject obj: gameObjects) {
            obj.update();
        }
        rain();
    }



}
