package com.hellscape.map;

import java.awt.Color;
import java.awt.Graphics2D;

import com.hellscape.control.Camera;
import com.hellscape.util.*;

public class Tile {
    
    public static final int SIZE = 160;

    private Box box;
    private Color color;
    private boolean onCamera;
    private boolean isPassable;

    public Tile(Point pos, Color color, boolean isPassable) {
        this.box = new Box(pos, SIZE, SIZE);
        this.color = color;
        this.onCamera = false;
        this.isPassable = isPassable;
    }

    public Tile (int posX, int posY, Color color, boolean isPassable) {
        this.box = new Box(posX, posY, SIZE, SIZE);
        this.color = color;
        this.onCamera = false;
        this.isPassable = isPassable;
    }

    public void update(Camera camera) {
        this.onCamera = camera.getCamBox().isCollide(this.box);
    }

    public void draw(Graphics2D g) {
        if (this.onCamera) {
            g.setColor(this.color);
            g.fillRect(this.box.getX(), this.box.getY(), this.box.getWidth(), this.box.getHeight());
            g.setColor(Color.BLUE);
            g.drawString("X: " + this.box.getX() + " Y: " + this.box.getY(), this.box.getX()+10, this.box.getY()+20);
        }
    }

    public boolean isCollide(Box box) {
        if (this.isPassable == true) return false;
        return this.box.isCollide(box);
    }
}
