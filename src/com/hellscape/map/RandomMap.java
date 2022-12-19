package com.hellscape.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.hellscape.util.*;

public class RandomMap {
    private int[][] map;
    private int width, height;
    private Point start, end;
    private Random rd;

    private List<Point> rooms;

    public RandomMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new int[height][width];
        this.rd = new Random();
        this.rooms = new ArrayList<Point>();
    }

    public int[][] generate(int roomSize, int totalRoom) {
        List<Point> rooms = this.generateRoom(roomSize, totalRoom);
        List<Line> connections = this.generateConnection(rooms);
        
        this.start = new Point(rooms.get(0));
        this.end = new Point(rooms.get(0));
        
        this.drawRoom(new Point(this.width/2, this.height/2), this.width, this.height, 1);
        for (Point room : rooms) this.drawRoom(room, roomSize, roomSize, 0);
        for (Line connection : connections) this.drawConnection(connection.getPointP(), connection.getPointQ(), 0);
        this.addNoise();
        
        this.start.translate(0, 2);
        this.end.translate(0, -1);
        this.drawRoom(start, 1, 3, 0);
        this.drawRoom(end, 1, 3, 0);
        
        this.start.translate(0, -1);
        this.end.translate(0, -1);
        this.paint();

        this.rooms = rooms;
        return this.map.clone();
    }

    private List<Point> generateRoom(int roomSize, int totalRoom) {
        List<Point> rooms = new ArrayList<Point>();
        Point pos = new Point(this.width/2, this.height/2);

        double r = 2*roomSize;
        double vX = 1;
        double vY = 0;
        
        rooms.add(new Point(pos));
        for (int i = 0; i < 2*totalRoom; i++) {
            rooms.add(new Point(pos.x + (int)(vX*r), pos.y + (int)(vY*r)));
            
            double rad = -2*Math.PI/(7+3*rd.nextDouble());
            double rX = vX * Math.cos(rad) - vY * Math.sin(rad);
            double rY = vX * Math.sin(rad) + vY * Math.cos(rad);
            r += (7+3*rd.nextDouble())/(2*roomSize);
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
            shortest.add(lines.get(rd.nextInt(rooms.size()/2)));
        }
        connections.addAll(shortest.subList(0, rooms.size()/2+1));

        return connections;
    }

    private void drawRoom(Point pos, int width, int height, int value) {
        for (int i = -height/2; i <= height/2; i++) {
            for (int j = -width/2; j <= width/2; j++) {
                this.map[i + pos.y][j + pos.x] = value;
                if (value == 1) continue;
                if ((i + pos.y) > this.start.y) this.start.move(pos.x, i + pos.y);
                if ((i + pos.y) < this.end.y) this.end.move(pos.x, i + pos.y);
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
        int[][] temp = this.map.clone();
        float[][] kern = {
            {1f/9, 1f/9, 1f/9},
            {1f/9, 1f/9, 1f/9},
            {1f/9, 1f/9, 1f/9}
        };

        for (int i = 1; i < this.height-1; i++) {
            for (int j = 1; j < this.width-1; j++) {
                this.map[i][j] = Math.round(
                    temp[i-1][j-1]*kern[0][0]+temp[i-1][j]*kern[0][1]+temp[i-1][j+1]*kern[0][2]+
                    temp[  i][j-1]*kern[1][0]+temp[  i][j]*kern[1][1]+temp[  i][j+1]*kern[1][2]+
                    temp[i+1][j-1]*kern[2][0]+temp[i+1][j]*kern[2][1]+temp[i+1][j+1]*kern[2][2]
                );
            }
        }
    }

    private void paint() {
        for (int i = 1; i < this.height-1; i++) {
            for (int j = 1; j < this.width-1; j++) {
                int code = (map[i][j] & 1);
                if ((map[i][j] & 1) == 0) {
                    code |= (rd.nextInt(16) << 1);
                    code |= (rd.nextInt(2) << 5);
                } else {
                    if ((map[i][j-1] & 1) == 0) code |= (1<<1);
                    if ((map[i][j+1] & 1) == 0) code |= (1<<2);
                    if ((map[i-1][j] & 1) == 0) code |= (1<<3);
                    if ((map[i+1][j] & 1) == 0) code |= (1<<4);

                    if ((map[i+1][j+1] & 1) == 0) code |= (1<<5);
                    if ((map[i-1][j+1] & 1) == 0) code |= (1<<6);
                    if ((map[i+1][j-1] & 1) == 0) code |= (1<<7);
                    if ((map[i-1][j-1] & 1) == 0) code |= (1<<8);
                }
                map[i][j] = code;
            }
        }
    }

    public Point getStart() {
        return this.start;
    }

    public Point getEnd() {
        return this.end;
    }

    public List<Point> getRooms() {
        return this.rooms;
    }
}
