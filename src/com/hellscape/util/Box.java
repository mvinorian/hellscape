package com.hellscape.util;

public class Box {
    
    private Point p;
    private int width, height;

    public Box(Point p, int width, int height) {
        this.p = p;
        this.width = width;
        this.height = height;
    }

    public Box(int x, int y, int width, int height) {
        this.p = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    public void translate(int dX, int dY) {
        this.p.translate(dX, dY);
    }

    Point getPoint() {
        return this.p;
    }

    int getWidth() {
        return this.width;
    }

    int getHeight() {
        return this.height;
    }
}
