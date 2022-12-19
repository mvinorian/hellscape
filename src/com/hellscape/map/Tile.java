package com.hellscape.map;

import java.awt.image.BufferedImage;

public class Tile {
    
    public BufferedImage sprite;
    public boolean isPassable;

    public Tile(BufferedImage sprite, boolean isPassable) {
        this.sprite = sprite;
        this.isPassable = isPassable;
    }
}
