package com.hellscape.control;

import java.awt.Graphics2D;
import java.util.List;

import com.hellscape.character.Enemy;
import com.hellscape.character.Player;
import com.hellscape.map.Map;
import com.hellscape.util.*;

public class Camera {
    
    private Map map;
    private Player player;
    private List<Enemy> enemies;

    private Box box;

    public Camera(int width, int height, int speed) {
        this.map = new Map();
        this.enemies = this.map.getEnemies();

        this.player = new Player(map.getStart(), speed);

        this.box = new Box(this.player.getPos(), width, height);
        this.box.translate((Player.SIZE-this.box.getWidth())/2, (Player.SIZE-this.box.getHeight())/2);
    }

    public void update() {
        this.player.update(this);
        this.box.move(this.player.getPos());
        this.box.translate((Player.SIZE-this.box.getWidth())/2, (Player.SIZE-this.box.getHeight())/2);
        this.map.update(this);
        this.enemies = this.map.getEnemies();
    }

    public void render(Graphics2D g) {
        g.translate(-this.box.getX(), -this.box.getY());
        this.map.draw(g);
        for (Enemy enemy : this.enemies) if (enemy.isAbove(this.player.getCBox()) == false) enemy.draw(g);
        this.player.draw(g);
        for (Enemy enemy : this.enemies) if (enemy.isAbove(this.player.getCBox()) == true) enemy.draw(g);
        g.translate(this.box.getX(), this.box.getY());
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
