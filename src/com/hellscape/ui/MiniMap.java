package com.hellscape.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

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
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(this.box.getX(), this.box.getY(), this.box.getWidth(), this.box.getHeight(), 16, 16);
        for (int i = 0; i < 57; i++) for (int j = 0; j < 57; j++) {
            if (this.grid[j][i] == 0) continue;
            g.fillRect(this.box.getX()+unit*i, this.box.getY()+unit*j, unit, unit);
        }
        g.setColor(Color.BLUE);
        g.fillRect(this.box.getX()+unit*playerX, this.box.getY()+unit*playerY, unit, unit);
        
        String floor = String.format("%d / 7 F", Map.getFloorCount());
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 24F));
        g.drawString(floor, 
            this.box.getX()+this.box.getWidth()/2
                -(int)g.getFontMetrics().getStringBounds(floor, g).getWidth()/2, 
            this.box.getY()+this.box.getHeight()+30);
    }
}
