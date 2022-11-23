package com.hellscape.util;

public class Line {
    
    public Point p, q;

    public Line(Line l) {
        this.p = new Point(l.getPointP());
        this.q = new Point(l.getPointQ());
    }

    public Line(Point p, Point q) {
        this.p = new Point(p);
        this.q = new Point(q);
    }

    public Line(int x1, int y1, int x2, int y2) {
        this.p = new Point(x1, y1);
        this.q = new Point(x2, y2);
    }

    public void translate(int dX, int dY) {
        this.p.translate(dX, dY);
        this.q.translate(dX, dY);
    }

    public double getLength() {
        return this.p.getDistance(q);
    }

    public Point getPointP() {
        return this.p;
    }

    public Point getPointQ() {
        return this.q;
    }
}
