package com.hellscape.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.hellscape.control.Camera;
import com.hellscape.util.Box;
import com.hellscape.util.Line;
import com.hellscape.util.Point;

public class Map {
    
    public static final int WIDTH = 57;
    public static final int HEIGHT = 49;

    private Point start;
    private int[][] map;
    private List<Tile> tileMap;

    private Random rd;

    public Map() {
        this.rd = new Random();
        this.map = new int[HEIGHT][WIDTH];
        this.randomize(5, 7);
        this.tileMap = new ArrayList<Tile>();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if ((map[i][j]) == 0) this.tileMap.add(new Tile(j*Tile.SIZE, i*Tile.SIZE, Color.GRAY, false));
                else this.tileMap.add(new Tile(j*Tile.SIZE, i*Tile.SIZE, Color.WHITE, true));
            }
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

    // Map Randomizer
    public void randomize(int roomSize, int totalRoom) {
        List<Point> rooms = this.generateRoom(roomSize, totalRoom);
        List<Line> connections = this.generateConnection(rooms);

        this.start = new Point(rooms.get(0));
        this.start.scale(Tile.SIZE, Tile.SIZE);
        for (Point room : rooms) this.drawRoom(room, roomSize, roomSize, 1);
        for (Line connection : connections) this.drawConnection(connection.getPointP(), connection.getPointQ(), 1);
        this.addNoise();
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

    private List<Line> generateConnection(List<Point> rooms) {
        List<Line> connections = new ArrayList<Line>();
        List<Line> edges = new ArrayList<Line>();

        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i+1; j < rooms.size(); j++) {
                edges.add(new Line(rooms.get(i), rooms.get(j)));
            }
        }

        Collections.sort(edges, new Comparator<Line>() {
            @Override
            public int compare(Line l1, Line l2) {
                return Double.compare(l1.getLength(), l2.getLength());
            }
        });
        
        HashMap<String, String> parent = new HashMap<String, String>();

        for (Line edge : edges) {
            String parentP = edge.getPointP().toString();
            String parentQ = edge.getPointQ().toString();
            parent.putIfAbsent(parentP, parentP);
            parent.putIfAbsent(parentQ, parentQ);

            while (parent.get(parentP).equals(parentP) == false) {
                parentP = parent.get(parentP);
            }
            while (parent.get(parentQ).equals(parentQ) == false) {
                parentQ = parent.get(parentQ);
            }

            if (parentP.equals(parentQ) == false) {
                connections.add(edge);
                parent.replace(parentP, parentQ);
            }
        }
        
        List<Line> shortest = new ArrayList<Line>();
        for (Point roomP : rooms) {
            List<Line> lines = new ArrayList<Line>();
            for (Point roomQ : rooms) {
                if (roomP.equals(roomQ)) continue;
                lines.add(new Line(roomP, roomQ));
            }
            Collections.sort(lines, new Comparator<Line>() {
                @Override
                public int compare(Line l1, Line l2) {
                    return Double.compare(l1.getLength(), l2.getLength());
                }
            });
            shortest.add(lines.get(rd.nextInt(0, rooms.size()/2)));
        }
        connections.addAll(shortest.subList(0, rooms.size()/2+1));

        return connections;
    }

    private void drawRoom(Point pos, int width, int height, int value) {
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

    private void drawConnection(Point start, Point target, int value) {
        Point s = new Point(start);
        Point t = new Point(target);
        int dX = Math.abs(t.x - s.x);
        int dY = -Math.abs(t.y - s.y);
        int sX = s.x < t.x ? 1 : -1;
        int sY = s.y < t.y ? 1 : -1;
        int error = dX + dY;

        while (!s.equals(t)) {
            this.map[s.y][s.x] = value;
            this.map[s.y+1][s.x] = value;
            this.map[s.y][s.x+1] = value;
            this.map[s.y+1][s.x+1] = value;
            int e = 2*error;
            if (e >= dY) {
                if (s.x == t.x) break;
                error += dY;
                s.translate(sX, 0);
            }
            if (e <= dX) {
                if (s.y == t.y) break;
                error += dX;
                s.translate(0, sY);
            }
        }
    }
    
    private void addNoise() {
        int[][] temp = map.clone();
        float[][] kern = {
            {1f/9, 1f/9, 1f/9},
            {1f/9, 1f/9, 1f/9},
            {1f/9, 1f/9, 1f/9}
        };

        for (int i = 1; i < HEIGHT-1; i++) {
            for (int j = 1; j < WIDTH-1; j++) {
                map[i][j] = Math.round(
                    temp[i-1][j-1]*kern[0][0]+temp[i-1][j]*kern[0][1]+temp[i-1][j+1]*kern[0][2]+
                    temp[  i][j-1]*kern[1][0]+temp[  i][j]*kern[1][1]+temp[  i][j+1]*kern[1][2]+
                    temp[i+1][j-1]*kern[2][0]+temp[i+1][j]*kern[2][1]+temp[i+1][j+1]*kern[2][2]
                );
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
