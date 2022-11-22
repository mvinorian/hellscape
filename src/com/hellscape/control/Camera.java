package com.hellscape.control;

import java.awt.*;

import com.hellscape.character.Player;
import com.hellscape.map.Map;
import com.hellscape.map.Tile;

public class Camera {
    
    private Map map;
    private Player player;

    private int width;
    private int height;

    private int camX;
    private int camY;

    public Camera(int width, int height, int speed) {
        this.width = width;
        this.height = height;

        this.map = new Map();
        
        this.camX = map.getStartX()-(this.width-Tile.SIZE)/2;
        this.camY = map.getStartY()-(this.height-Tile.SIZE)/2;

        this.player = new Player(this.camX+(this.width-Tile.SIZE)/2, this.camY+(this.height-Tile.SIZE)/2, speed);
    }

    public void update() {
        this.player.update(this);
        this.camX = this.player.getPosX() + (Tile.SIZE-this.width)/2;
        this.camY = this.player.getPosY() + (Tile.SIZE-this.height)/2;
        this.map.update(this);
    }

    public void render(Graphics2D g) {
        g.translate(-camX, -camY);
        this.map.draw(g);
        this.player.draw(g);
        g.translate(camX, camY);
        g.drawString("X: " + this.camX + " Y: " + this.camY, 10, 20);
    }

    public int getCamX() {
        return this.camX;
    }

    public int getCamY() {
        return this.camY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
    
    public Map getMap() {
        return this.map;
    }

    public Player getPlayer() {
        return this.player;
    }
}
