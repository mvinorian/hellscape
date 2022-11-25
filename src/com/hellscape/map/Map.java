package com.hellscape.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.hellscape.asset.Tileset;
import com.hellscape.control.Camera;
import com.hellscape.util.*;

public class Map {
    
    public static final int WIDTH = 57;
    public static final int HEIGHT = 49;

    private Point start;
    private Point end;
    private int[][] map;
    private List<Tile> tileMap;

    public Map() {
        Tileset.load();
        this.map = new int[HEIGHT][WIDTH];
        this.generate();
        this.tileMap = new ArrayList<Tile>();

        for (int i = 0; i < HEIGHT; i++) for (int j = 0; j < WIDTH; j++) {
            boolean isPassable = (map[i][j] & 1) == 0;
            this.tileMap.add(
                new Tile(this.getTilePos(i, j), this.getSpritePos(map[i][j]),
                isPassable));
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(String.format("%d ", this.map[i][j]));
            }
            System.out.println();
        }
    }

    public void update(Camera camera) {
        for (Tile tile : this.tileMap) tile.update(camera);
    }

    public void draw(Graphics2D g) {
        for (Tile tile : this.tileMap) tile.draw(g);
    }

    public boolean isCollide(Box box) {
        boolean isCollide = false;
        for (Tile tile : this.tileMap) isCollide |= tile.isCollide(box);
        return isCollide;
    }

    public void generate() {
        int roomSize = 5;
        int totalRoom = 7;
        RandomMap rm = new RandomMap(WIDTH, HEIGHT);

        this.map = rm.generate(roomSize, totalRoom);
        this.start = rm.getStart();
        this.end = rm.getEnd();
    }

    private Point getTilePos(int i, int j) {
        return new Point(j, i);
    }

    private Point getSpritePos(int code) {
        int col = (code & 31) >> 1;
        int row = (code >> 5);
        if ((code & 1) == 0) row += 16;
        return new Point(col, row);
    }

    public Point getStart() {
        return this.start;
    }

    public int getStartX() {
        return this.start.x;
    }

    public int getStartY() {
        return this.start.y;
    }

    public Point getEnd() {
        return this.end;
    }

    public int getEndX() {
        return this.end.x;
    }

    public int getEndY() {
        return this.end.y;
    }
}
