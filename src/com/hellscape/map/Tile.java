package com.hellscape.map;

import java.awt.*;

import com.hellscape.control.Camera;

public class Tile {
    
    public static final int SIZE = 160;

    private Color color;
    private int posX;
    private int posY;
    private boolean onCamera;
    private boolean isPassable;

    public Tile (Color color, int posX, int posY, boolean isPassable) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.onCamera = false;
        this.isPassable = isPassable;
    }

    public void update(Camera camera) {
        int relX = this.posX - camera.getCamX();
        int relY = this.posY - camera.getCamY();
        this.onCamera = -Tile.SIZE <= relX && relX <= camera.getWidth() &&
                        -Tile.SIZE <= relY && relY <= camera.getHeight();
    }

    public void draw(Graphics2D g) {
        if (this.onCamera) {
            g.setColor(this.color);
            g.fillRect(this.posX, this.posY, Tile.SIZE, Tile.SIZE);
            g.setColor(Color.BLUE);
            g.drawString("X: " + this.posX + " Y: " + this.posY, this.posX+10, this.posY+20);
        }
    }

    public boolean collide(int posX, int posY, int width, int height) {
        boolean isCollide = !this.isPassable;
        if (isCollide) isCollide = (this.posX < posX + width &&
                                    this.posY < posY + height &&
                                    this.posX + Tile.SIZE > posX &&
                                    this.posY + Tile.SIZE > posY);
        return isCollide;
    }
}
