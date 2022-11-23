package com.hellscape.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.hellscape.control.Camera;
import com.hellscape.util.*;

public class Map {
    
    public static final int WIDTH = 57;
    public static final int HEIGHT = 49;

    private Point start;
    private int[][] map;
    private List<Tile> tileMap;

    private Random rd;

    public Map() {
        this.rd = new Random();
        this.map = new int[Map.HEIGHT][Map.WIDTH];
        this.randomize(5, 7);
        this.tileMap = new ArrayList<Tile>();
        for (int i = 0; i < Map.HEIGHT; i++) {
            for (int j = 0; j < Map.WIDTH; j++) {
                if ((map[i][j]) == 0) this.tileMap.add(new Tile(j*Tile.SIZE, i*Tile.SIZE, Color.GRAY, false));
                else this.tileMap.add(new Tile(j*Tile.SIZE, i*Tile.SIZE, Color.WHITE, true));
            }
        }

        for (int i = 0; i < Map.HEIGHT; i++) {
            for (int j = 0; j < Map.WIDTH; j++) {
                System.out.print(String.format("%d", this.map[i][j]));
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

    public void randomize(int roomSize, int totalRoom) {
        List<Point> rooms = this.generateRoom(roomSize, totalRoom);

        this.start = new Point(rooms.get(0));
        this.start.scale(Tile.SIZE, Tile.SIZE);
        for (Point room : rooms) {
            this.fillRoom(room, roomSize, roomSize, 1);
        }
    }

    private List<Point> generateRoom(int roomSize, int totalRoom) {
        List<Point> rooms = new ArrayList<Point>();
        Point pos = new Point(WIDTH/2, HEIGHT/2);

        double r = 2*roomSize;
        double vX = 1;
        double vY = 0;
        
        rooms.add(new Point(pos));
        for (int i = 0; i < 2*totalRoom; i++) {
            rooms.add(new Point(pos.x + (int)(vX*r), pos.y + (int)(vY*r)));
            
            double rad = -2*Math.PI/rd.nextDouble(7, 10);
            double rX = vX * Math.cos(rad) - vY * Math.sin(rad);
            double rY = vX * Math.sin(rad) + vY * Math.cos(rad);
            r += rd.nextDouble(7, 10)/(2*roomSize);
            vX = rX;
            vY = rY;
        }
        Collections.shuffle(rooms);
        return rooms.subList(0, totalRoom);
    }

    private void fillRoom(Point pos, int width, int height, int value) {
        for (int i = -width/2; i <= width/2; i++) {
            for (int j = -height/2; j <= height/2; j++) {
                this.map[i + pos.y][j + pos.x] = value;
                if ((i + pos.y)*Tile.SIZE > this.start.y) {
                    this.start.move(pos.x, i + pos.y);
                    this.start.scale(Tile.SIZE, Tile.SIZE);
                }
            }
        }
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
}
