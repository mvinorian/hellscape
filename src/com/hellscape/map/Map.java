package com.hellscape.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.hellscape.character.Enemy;
import com.hellscape.ui.Camera;
import com.hellscape.util.*;

public class Map {
    
    public static final int WIDTH = 57;
    public static final int HEIGHT = 57;
    
    private static int floorCount = 1;

    private Point start;
    private Point end;
    private int[][] map;
    private Door door;
    private List<Tile> tileMap;
    private List<Enemy> enemies;

    public Map() {
        this.map = new int[HEIGHT][WIDTH];
        this.generate();
        this.door = new Door(this.end);
        this.tileMap = new ArrayList<Tile>();

        for (int i = 0; i < HEIGHT; i++) for (int j = 0; j < WIDTH; j++) {
            boolean isPassable = (map[i][j] & 1) == 0;
            this.tileMap.add(
                new Tile(this.getTilePos(i, j), this.getSpritePos(map[i][j]),
                isPassable));
        }

        // for (int i = 0; i < HEIGHT; i++) {
        //     for (int j = 0; j < WIDTH; j++) {
        //         System.out.print(String.format("%d ", this.map[i][j]));
        //     }
        //     System.out.println();
        // }
    }

    public void update(Camera camera) {
        for (Tile tile : this.tileMap) tile.update(camera);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDead() == false) enemy.update(camera);
            else {
                enemies.remove(enemy);
                i--;
            }
        }
        this.door.update(camera);
        if (this.door.isEntered() == true) {
            this.reset();
            camera.getPlayer().move(this.start);
            floorCount++;
        }
    }

    public void draw(Graphics2D g) {
        for (Tile tile : this.tileMap) tile.draw(g);
        this.door.draw(g);
    }

    public boolean isCollide(Box box) {
        boolean isCollide = false;
        for (Tile tile : this.tileMap) isCollide |= tile.isCollide(box);
        for (Enemy enemy : this.enemies) isCollide |= enemy.isCollide(box);
        isCollide |= this.door.isCollide(box);
        return isCollide;
    }

    private void reset() {
        this.generate();
        this.door = new Door(this.end);
        this.tileMap = new ArrayList<Tile>();

        for (int i = 0; i < HEIGHT; i++) for (int j = 0; j < WIDTH; j++) {
            boolean isPassable = (map[i][j] & 1) == 0;
            this.tileMap.add(
                new Tile(this.getTilePos(i, j), this.getSpritePos(map[i][j]),
                isPassable));
        }
    }

    private void generate() {
        int roomSize = 5;
        int totalRoom = 7;
        RandomMap rm = new RandomMap(WIDTH, HEIGHT);

        this.map = rm.generate(roomSize, totalRoom);
        this.enemies = rm.getEnemies();
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

    public List<Enemy> getEnemies() {
        return this.enemies;
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

    public int[][] getGrid() {
        int[][] grid = new int[WIDTH][HEIGHT];

        for (int i = 0; i < HEIGHT; i++) for (int j = 0; j < WIDTH; j++) {
            grid[i][j] = (this.map[i][j] & 1) == 0 ? 1 : 0;
        }

        return grid;
    }

    public static int getFloorCount() {
        return floorCount;
    }
}
