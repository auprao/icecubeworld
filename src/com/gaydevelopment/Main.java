package com.gaydevelopment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {

    static int width = 1280;
    static int height = 720;

    static LinkedList<GameObject> gameObjects = new LinkedList<>();

    static AnchorPane root = new AnchorPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void rain(){

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

    public void newFrame() {
        for (GameObject obj: gameObjects) {

        }
        rain();
    }



}
