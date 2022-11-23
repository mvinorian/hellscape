package com.hellscape.util;

public class Point {

    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void translate(int dX, int dY) {
        this.x += dX;
        this.y += dY;
    }
}
