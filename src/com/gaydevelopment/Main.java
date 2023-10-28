package com.gaydevelopment;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;

public class Main extends Application {

    static int width = 1280;
    static int height = 720;

    static final int FPS = 60;

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
    }

    public void loadMapFromArray(GameObject[][] array){
        int generationX = 0;
        int generationY = 0;
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[0].length; c++) {

                generationX = generationX + width / array.length;
            }
            generationY = generationY + height / array[0].length;
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
    }



}
