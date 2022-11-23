package com.hellscape.util;

public class Line {
    
    public Point p, q;

    public Line(Point p, Point q) {
        this.p = p;
        this.q = q;
    }

    public Line(int x1, int y1, int x2, int y2) {
        this.p = new Point(x1, y1);
        this.q = new Point(x2, y2);
    }

    public void translate(int dX, int dY) {
        this.p.translate(dX, dY);
        this.q.translate(dX, dY);
    }
}
