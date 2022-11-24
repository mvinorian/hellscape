package com.hellscape.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

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
        this.map = new int[HEIGHT][WIDTH];
        this.generate();
        this.tileMap = new ArrayList<Tile>();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if ((map[i][j]) == 0) this.tileMap.add(new Tile(j*Tile.SIZE, i*Tile.SIZE, Color.GRAY, false));
                else this.tileMap.add(new Tile(j*Tile.SIZE, i*Tile.SIZE, Color.WHITE, true));
            }
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(String.format("%c ", this.map[i][j] == 0 ? '0' : ' '));
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
