package com.hellscape.map;

import java.awt.Graphics2D;

import com.hellscape.asset.Tileset;
import com.hellscape.control.Camera;
import com.hellscape.util.*;

public class Tile {
    
    public static final int TILE_SIZE = 160;
    public static final int SPRITE_SIZE = 32;

    private Box box;
    private boolean onCamera;
    private boolean isPassable;
    private Box spriteBox;

    public Tile(Point tilePos, Point spritePos, boolean isPassable) {
        tilePos.scale(TILE_SIZE, TILE_SIZE);
        spritePos.scale(SPRITE_SIZE, SPRITE_SIZE);
        this.box = new Box(tilePos, TILE_SIZE, TILE_SIZE);
        this.spriteBox = new Box(spritePos, 32, 32);
        this.onCamera = false;
        this.isPassable = isPassable;
    }

    public void update(Camera camera) {
        this.onCamera = camera.getCamBox().isCollide(this.box);
    }

    public void draw(Graphics2D g) {
        if (this.onCamera == false) return;
        Tileset.draw(g, this.box, this.spriteBox);
        // g.setColor(Color.RED);
        // g.drawString("X: " + this.box.getX() + " Y: " + this.box.getY(), this.box.getX()+10, this.box.getY()+20);
    }

    public boolean isCollide(Box box) {
        if (this.isPassable == true) return false;
        return this.box.isCollide(box);
    }
}
