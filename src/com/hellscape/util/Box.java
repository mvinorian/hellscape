package com.hellscape.util;

public class Box {
    
    private Point pos;
    private int width, height;

    public Box(Point pos, int width, int height) {
        this.pos = pos;
        this.width = width;
        this.height = height;
    }

    public Box(int x, int y, int width, int height) {
        this.pos = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    public void translate(int dX, int dY) {
        this.pos.translate(dX, dY);
    }

    public boolean isCollide(Box box) {
        return (this.pos.x < box.getX() + box.getWidth() &&
                this.pos.y < box.getY() + box.getHeight() &&
                this.pos.x + this.width > box.getX() &&
                this.pos.y + this.height > box.getY());
    }

    Point getPoint() {
        return this.pos;
    }

    int getX() {
        return this.pos.x;
    }

    int getY() {
        return this.pos.y;
    }

    int getWidth() {
        return this.width;
    }

    int getHeight() {
        return this.height;
    }
}
