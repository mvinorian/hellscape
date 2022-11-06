package com.hellscape.control;

import java.awt.*;
import java.awt.event.*;

import com.hellscape.map.Map;
import com.hellscape.map.Tile;

public class Camera implements KeyListener {
    
    private Map map;
    private int width;
    private int height;
    private int speed;

    private int camX;
    private int camY;
    private int velX;
    private int velY;

    private boolean isMovingUp;
    private boolean isMovingDown;
    private boolean isMovingLeft;
    private boolean isMovingRight;

    public Camera(int width, int height, int speed) {
        this.width = width;
        this.height = height;
        this.speed = speed;

        this.map = new Map();
        
        this.camX = map.getStartX();
        this.camY = map.getStartY();
        this.velX = 0;
        this.velY = 0;

        this.isMovingUp = false;
        this.isMovingDown = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }

    public void update() {
        boolean collideX = map.collide(this.camX+(this.width-Tile.SIZE)/2+this.velX, this.camY+(this.height-Tile.SIZE)/2, Tile.SIZE-1, Tile.SIZE-1);
        boolean collideY = map.collide(this.camX+(this.width-Tile.SIZE)/2, this.camY+(this.height-Tile.SIZE)/2+this.velY, Tile.SIZE-1, Tile.SIZE-1);
        boolean collide = map.collide(this.camX+(this.width-Tile.SIZE)/2+this.velX, this.camY+(this.height-Tile.SIZE)/2+this.velY, Tile.SIZE-1, Tile.SIZE-1);

        map.update(this);
        if (!collideX || !collide) this.camX += this.velX;
        if (!collideY || !collide) this.camY += this.velY;
    }

    public void render(Graphics2D g) {
        g.translate(-camX, -camY);
        this.map.draw(g);
        g.translate(camX, camY);
        g.setColor(Color.RED);
        g.drawRect((this.width - Tile.SIZE)/2, (this.height - Tile.SIZE)/2, Tile.SIZE-1, Tile.SIZE-1);
        g.drawString("X: " + this.camX + " Y: " + this.camY, 10, 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        switch (key) {
            case 'w':
                this.velY = -this.speed;
                this.isMovingUp = true;
                break;
            case 's':
                this.velY = this.speed;
                this.isMovingDown = true;
                break;
            case 'a':
                this.velX = -this.speed;
                this.isMovingLeft = true;
                break;
            case 'd':
                this.velX = this.speed;
                this.isMovingRight = true;
                break;
        
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        switch (key) {
            case 'w':
                this.velY = 0;
                if (this.isMovingDown) this.velY = this.speed;
                this.isMovingUp = false;
                break;
            case 's':
                this.velY = 0;
                if (this.isMovingUp) this.velY = -this.speed;
                this.isMovingDown = false;
                break;
            case 'a':
                this.velX = 0;
                if (this.isMovingRight) this.velX = this.speed;
                this.isMovingLeft = false;
                break;
            case 'd':
                this.velX = 0;
                if (this.isMovingLeft) this.velX = -this.speed;
                this.isMovingRight = false;
                break;
        
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
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
}
