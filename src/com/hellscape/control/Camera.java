package com.hellscape.control;

import java.awt.Graphics2D;

import com.hellscape.character.Player;
import com.hellscape.map.Map;
import com.hellscape.util.*;

public class Camera {
    
    private Map map;
    private Player player;

    private Box box;

    public Camera(int width, int height, int speed) {
        this.map = new Map();

        this.player = new Player(map.getStart(), speed);

        this.box = new Box(this.player.getPos(), width, height);
        this.box.translate((Player.SIZE-this.box.getWidth())/2, (Player.SIZE-this.box.getHeight())/2);
    }

    public void update() {
        this.player.update(this);
        this.box.move(this.player.getPos());
        this.box.translate((Player.SIZE-this.box.getWidth())/2, (Player.SIZE-this.box.getHeight())/2);
        this.map.update(this);
    }

    public void render(Graphics2D g) {
        g.translate(-this.box.getX(), -this.box.getY());
        this.map.draw(g);
        this.player.draw(g);
        g.translate(this.box.getX(), this.box.getY());
//        g.drawString("X: " + this.box.getX() + " Y: " + this.box.getY(), 10, 20);
    }

    public void resize(int width, int height) {
        this.box.resize(width, height);
        this.update();
    }

    public Box getCamBox() {
        return this.box;
    }

    public Point getCamPos() {
        return this.box.getPoint();
    }

    public int getCamX() {
        return this.box.getX();
    }

    public int getCamY() {
        return this.box.getY();
    }

    public int getWidth() {
        return this.box.getWidth();
    }

    public int getHeight() {
        return this.box.getHeight();
    }
    
    public Map getMap() {
        return this.map;
    }

    public Player getPlayer() {
        return this.player;
    }
}
