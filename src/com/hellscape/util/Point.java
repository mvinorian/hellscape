package com.hellscape.util;

public class Point {

    public int x, y;

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int dX, int dY) {
        this.x += dX;
        this.y += dY;
    }

    public void scale(int rX, int rY) {
        this.x *= rX;
        this.y *= rY;
    }
}
