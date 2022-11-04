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

    private int posX;
    private int posY;
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
        
        this.posX = this.width/2;
        this.posY = this.height/2;
        this.velX = 0;
        this.velY = 0;

        this.map = new Map(this.posX - this.width/2, this.posY - this.height/2);

        this.isMovingUp = false;
        this.isMovingDown = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }

    public void update() {
        int newX = this.posX + this.velX;
        int newY = this.posY + this.velY;

        if ((newX >= this.width / 2) && (Math.abs(newX - Map.MAX_X) >= this.width / 2)) {
            this.posX += this.velX;
            this.map.updatePos(-this.velX, 0);
        }
        if ((newY >= this.height / 2) && (Math.abs(newY - Map.MAX_X) >= this.height / 2)) {
            this.posY += this.velY;
            this.map.updatePos(0, -this.velY);
        }
    }

    public void render(Graphics g) {
        this.map.draw(g);
        g.setColor(Color.RED);
        g.drawRect((this.width - Tile.SIZE)/2, (this.height - Tile.SIZE)/2, Tile.SIZE, Tile.SIZE);
        g.drawString("X: " + this.posX + " Y: " + this.posY, 10, 20);
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

}
