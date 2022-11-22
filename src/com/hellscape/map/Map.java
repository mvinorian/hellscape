package com.hellscape.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.hellscape.control.Camera;

public class Map {
    
    public static final int WIDTH = 57;
    public static final int HEIGHT = 49;

    private int startX;
    private int startY;
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
                if ((map[i][j]) == 0) this.tileMap.add(new Tile(Color.GRAY, j*Tile.SIZE, i*Tile.SIZE, false));
                else this.tileMap.add(new Tile(Color.WHITE, j*Tile.SIZE, i*Tile.SIZE, true));
            }
        }

        System.out.println(String.format("%d %d", this.startX, this.startY));
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

    public boolean collide(int posX, int posY, int width, int height) {
        boolean res = false;
        for (Tile tile : this.tileMap) res |= tile.collide(posX, posY, width, height);
        return res;
    }

    public void randomize(int roomSize, int totalRoom) {
        List<Point> rooms = this.generateRoom(roomSize, totalRoom);

        this.startX = rooms.get(0).x*Tile.SIZE;
        this.startY = rooms.get(0).y*Tile.SIZE;
        for (Point room : rooms) {
            this.fillRoom(room.x, room.y, roomSize, 1);
        }
    }

    private List<Point> generateRoom(int roomSize, int totalRoom) {
        List<Point> rooms = new ArrayList<Point>();
        int posX = Map.WIDTH/2;
        int posY = Map.HEIGHT/2;

        double r = 2*roomSize;
        double vX = 1;
        double vY = 0;
        
        rooms.add(new Point(posX, posY));
        for (int i = 0; i < 2*totalRoom; i++) {
            rooms.add(new Point(posX + (int)(vX*r), (posY + (int)(vY*r))));
            
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

    private void fillRoom(int posX, int posY, int size, int value) {
        for (int i = -size/2; i <= size/2; i++) {
            for (int j = -size/2; j <= size/2; j++) {
                this.map[posY+i][posX+j] = value;
                if ((posY+i)*Tile.SIZE > this.startY) {
                    this.startX = (posX)*Tile.SIZE;
                    this.startY = (posY+i)*Tile.SIZE;
                }
            }
        }
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }
}
