package com.hellscape.map;

import java.awt.*;
import com.hellscape.Main;

public class Tile {

    public static final int SIZE = 160;

    private Color color;
    private int posX;
    private int posY;

    public Tile(Color color, int posX, int posY) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
    }

    public void updatePos(int dX, int dY) {
        this.posX += dX;
        this.posY += dY;
    }

    public void draw(Graphics g) {
        if (-Tile.SIZE <= this.posX && this.posX <= Main.WIDTH + Tile.SIZE &&
            -Tile.SIZE <= this.posY && this.posY <= Main.HEIGHT + Tile.SIZE) {

            g.setColor(this.color);
            g.fillRect(this.posX, this.posY, Tile.SIZE, Tile.SIZE);
        }
    }

}
