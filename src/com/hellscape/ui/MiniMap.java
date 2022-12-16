package com.hellscape.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import com.hellscape.control.Camera;
import com.hellscape.map.Map;
import com.hellscape.map.Tile;
import com.hellscape.util.Box;

public class MiniMap {

    private Box box;
    private int[][] grid;
    private int playerX;
    private int playerY;
    private int unit;
    
    public MiniMap(int[][] grid, Box box) {
        this.box = box;
        this.grid = grid;
        this.playerX = 0;
        this.playerY = 0;
        this.unit = Math.min(box.getWidth()/Map.WIDTH, box.getHeight()/Map.HEIGHT);
    }

    public void update(Camera camera) {
        this.grid = camera.getMap().getGrid();
        this.playerX = camera.getPlayer().getCBox().getX();
        this.playerY = camera.getPlayer().getCBox().getY();
        this.playerX /= Tile.TILE_SIZE;
        this.playerY /= Tile.TILE_SIZE;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        for (int i = 0; i < 57; i++) for (int j = 0; j < 57; j++) {
            if (this.grid[j][i] == 0) continue;
            g.fillRect(this.box.getX()+unit*i, this.box.getY()+unit*j, unit, unit);
        }
        g.setColor(Color.BLUE);
        g.fillRect(this.box.getX()+unit*playerX, this.box.getY()+unit*playerY, unit, unit);
    }
}
