package com.gaydevelopment;

import javafx.scene.image.Image;

public class GameObject {
    int x, y;
    int width, height;
    Image sprite;

    public GameObject(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }
}
