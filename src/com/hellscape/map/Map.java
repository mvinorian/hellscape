package com.hellscape.map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

public class Map {

    public static final int MAX_X = 3200;
    public static final int MAX_Y = 3200;

    private List<Tile> map;

    public Map(int cameraPosX, int cameraPosY) {
        this.map = new ArrayList<Tile>();
        for (int i = 0; i < Map.MAX_X / Tile.SIZE; i++) {
            for (int j = 0; j < Map.MAX_Y / Tile.SIZE; j++) {
                if ((i + j) % 2 == 0) this.map.add(new Tile(Color.GRAY, i * Tile.SIZE - cameraPosX, j * Tile.SIZE - cameraPosY));
                else this.map.add(new Tile(Color.WHITE, i * Tile.SIZE - cameraPosX, j * Tile.SIZE - cameraPosY));
            }
        }
    }

    public void updatePos(int dX, int dY) {
        for (Tile tile : this.map) tile.updatePos(dX, dY);
    }

    public void draw(Graphics g) {
        for (Tile tile : this.map) tile.draw(g);
    }

}
