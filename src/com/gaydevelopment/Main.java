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
import java.util.Objects;

import static com.gaydevelopment.Tiles.*;

public class Main extends Application {

    static int width = 1575;
    static int height = 620;

    static final int FPS = 60;

    static Timeline mainTimeline = new Timeline();
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
            particle.setFill(Color.rgb((int) (Math.random() * 100 + 155) - 50, (int) (Math.random() * 100 + 155), 255, 0.5));
            particle.setX(Math.random() * width);
            particle.setY(Math.random() * -height);
            rainParticles.add(particle);
            root.getChildren().add(particle);
        }

        String[] greenMap = {
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "aarraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrraaaaa",
                "arggaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrgggraaaa",
                "aggbaaaaaaaaaaaaaaaaaaaaaaaaaaaaarggbbggaaaa",
                "aagbaaaaaaaaaaaaaasaaaaaaaaaaaaaagbbbbggaaaa",
                "aagbbaaaaaaaaaaaarrraaaaaaaarrraabbbbrgaaaaa",
                "argbbbbaaaarraaaagggraaaaaaaggaaaabbbggwaasa",
                "aggrbbbbrrrggraaaaagaaaaaaaaaaaaaafbrgggwwww",
                "agggrrrrggggbbbaaaaaaaaaaaaaaaaaarrrggggggga",
                "aggggggggggbbbrraaaaaarraaaaaaarrgggggaaaaaa",
                "aaaggggggbbbrrggraaaarggaaarrrrggggaaaaaaaaa"
        };

        String[] snowMap = {
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaaaaaaaaaawwwaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaaaaaaaaaagggwaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaaaaaaaaawggggwaaaaaaaaaaaaaaaawwaaaaaaaaaaaa",
                "aaaaaaawwwaasaaaaaaawwggggggaaaaaaaaaaaaaaaaggwaafawaaaasa",
                "aaaaaaagggwwwwwbaaaaggggggggaaaaaaaaaaaaaaaagggwwwwgaaasss",
                "aaaaawwgggggbbbbbwwwggbbbggbaaawwwaaaaaaaaawggggggggwaaasa",
                "aaaawgggggbbbbbbbbbggbbbbbbbbbwgggaaawwaaaaggggggggggaaaaa",
                "aaaagggggbbbbbbbbbbbbbfbbbbbbbggggwaaggwaaagggggggggaaaaaa",
                "aawwgggggwwwbbbbbbbbbwwwwwbbbwgggggwwgggwwagggggggggaaaaaa",
                "awggggggggggwwwwbbwwwgggggwbbgggggggggggggaaggggggggwaaaaa"
        };


        // add maps together
        for (int r = 0; r < 11; r++) {
            greenMap[r] += snowMap[r];
        }

        loadMapFromKey(greenMap);

        mainTimeline.setCycleCount(-1);
        mainTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1 / (double) FPS), e -> newFrame()));
        mainTimeline.play();
        //temp

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if ((key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT)) {
                player.velocityX = -10;
            }
            if ((key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT)) {
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

    public Tiles[][] keyIntoArray(String[] key){
        /*Tiles[][] array = {
                {Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air, Grass, Grass,Air,Air,Air, Air ,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Grass,Grass,Grass,Grass,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Grass, Ground, Ground, Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Grass,Grass,Ground,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Ground, Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Grass,Ground,Ground,Air,Air,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air, Ground, Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Ground,Air,Air,Air,Air,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Grass,Grass,Grass,Air,Air,Air,Air,Air,Air,Air,Air,Grass, Grass,Grass,Air,Air,Air,Air,Air,Air,Grass,Ground,Air,Air,Air, Air ,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Grass, Ground,Air,Air,Air,Air,Air,Air,Snowflake,Air,Grass,Grass,Air,Air,Air,Air,Ground,Ground,Ground,Grass,Air,Air,Air,Air,Air,Air,Air,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Ground,Ground,Grass,Air,Air,Snowflake,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Ground,Ground,Grass,Air,Air,Air,Air,Grass,Grass,Grass,Ground,Ground,Grass,Air,Air,Air,Air,Air,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Fridge,Air,Grass,Ground,Ground,Ground,Grass, Grass, Grass,Grass,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Ground,Ground,Ground, Grass, Grass, Grass, Grass,Ground,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Grass,Grass,Grass,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Air,Air,Air,Grass,Grass,Air,Air,Air,Air,Air,Air,Grass,Grass,Air,Air,Air,Air,Grass,Grass,Grass,Grass,Grass,Ground,Ground,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air},
                {Air,Air, Air, Ground, Ground, Ground,Ground,Ground,Ground,Air,Air,Air,Grass,Grass,Ground,Ground,Grass,Air,Air,Air,Air,Grass,Ground,Ground,Air,Air,Air,Grass,Ground,Ground,Ground,Ground,Ground,Ground,Ground,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air,Air}
        };  */
        Tiles[][] array = new Tiles[11][key[0].length()];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < key[0].length(); j++) {
                char c = key[i].charAt(j);
                switch (c) {
                    case 'a' :
                        array[i][j] = Air;
                        break;
                    case 'g' :
                        array[i][j] = Ground;
                        break;
                    case 's' :
                        array[i][j] = Snowflake;
                        break;
                    case 'f' :
                        array[i][j] = Fridge;
                        break;
                    case 'r' :
                        array[i][j] = Grass;
                        break;
                    case 'w' :
                        array[i][j] = SnowGrass;
                        break;
                    case 'b' :
                        array[i][j] = BackGround;
                        break;
                }
            }
        }
        return array;
    }

    public void loadMapFromKey(String[] key){
        Tiles[][] array = keyIntoArray(key);

        double generationX = 0;
        double generationY = 0;
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[0].length; c++) {
                if (array[r][c] == BackGround) {
                    addGameObject(new BackGround((int) generationX, (int) generationY));
                }
                generationX = c * (0.3 * 200);
            }
            generationY = (r) * (height / array.length);
        }

        IceCube icecube = new IceCube(200, 200, "file:iceCubeSprite.png");
        icecube.setScaleX(0.3);
        icecube.setScaleY(0.3);
        addGameObject(icecube);
        player = icecube;

        generationX = 0;
        generationY = 0;
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
                    case SnowGrass:
                        addGameObject(new SnowGrass((int) generationX, (int) generationY));
                        break;
                    case BackGround:
                        break;
                }
                generationX = c * (0.3 * 200);
            }
            generationY = (r) * (height / array.length);
        }
    }

    public static void addGameObject(GameObject obj) {
        gameObjects.add(obj);
        root.getChildren().add(obj);
    }

    public static void removeGameObject(GameObject obj) {
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

        if (player.getX() > width - 9) {
            for (GameObject obj: gameObjects) {
                obj.setX(obj.getX() - width);
            }
        }
        else if (player.getX() < 9) {
            for (GameObject obj: gameObjects) {
                obj.setX(obj.getX() + width);
            }
        }
    }
}
