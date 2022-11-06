package com.hellscape.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.hellscape.control.Camera;

public class Map {
    
    public static final int MAX_W = 5120;
    public static final int MAX_H = 5120;

    private int startX;
    private int startY;
    private int[] map;
    private List<Tile> tileMap;

    public Map() {
        this.map = new int[] {
            0b00000000000000000000000000000000,
            0b00000000000000000000000000000000,
            0b00000000000000000000000000000000,
            0b00000000000000000001111000000000,
            0b00000000000000000011111100000000,
            0b00000000000000000011111100000000,
            0b00000000000000000011111100000000,
            0b00000000000000000111111100000000,
            0b00001110000000000111111000000000,
            0b00011111000000001100000000000000,
            0b00011111100000001100000000000000,
            0b00011111110000001100000000000000,
            0b00111111111100001100000000000000,
            0b00111111111111001100000000000000,
            0b00011111111111111101111111000000,
            0b00011111100011111111111111000000,
            0b00011111000000111111111111100000,
            0b00000111100000001111111111100000,
            0b00000011111000001111111111100000,
            0b00000001111100000011111111110000,
            0b00000001111100000111110001110000,
            0b00000011111100001111110001110000,
            0b00000111111100001111110001110000,
            0b00001111111100000011110001110000,
            0b00001111111100000000000011110000,
            0b00011111111100000000001111110000,
            0b00011111111100000000011111110000,
            0b00011111111100000000001111110000,
            0b00000111111100000000000111000000,
            0b00000011110000000000000000000000,
            0b00000000100000000000000000000000,
            0b00000000000000000000000000000000,
        };

        int n = Map.MAX_W/Tile.SIZE;
        int m = Map.MAX_H/Tile.SIZE;
        this.tileMap = new ArrayList<Tile>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if ((map[j] & 1<<i) == 0) this.tileMap.add(new Tile(Color.GRAY, i*Tile.SIZE, j*Tile.SIZE, false));
                else this.tileMap.add(new Tile(Color.WHITE, i*Tile.SIZE, j*Tile.SIZE, true));
            }
        }

        this.startX = 3360;
        this.startY = 4640;
    }

    public void update(Camera camera) {
        for (Tile tile : this.tileMap) tile.update(camera);
    }

    public void draw(Graphics2D g) {
        for (Tile tile : this.tileMap) tile.draw(g);
    }

    public boolean collide(int posX, int posY, int width, int height) {
        boolean res = false;
        for (Tile tile : this.tileMap) res |= tile.collide(posX, posY, width, height);
        return res;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }
}
