package com.hellscape.character;

import java.awt.*;
import java.awt.event.*;

import com.hellscape.control.Camera;
import com.hellscape.map.Tile;

public class Player implements KeyListener {
    
    private int posX;
    private int posY;
    private int speed;

    private int velX;
    private int velY;

    private boolean isMovingUp;
    private boolean isMovingDown;
    private boolean isMovingLeft;
    private boolean isMovingRight;

    public Player(int posX, int posY, int speed) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;

        this.velX = 0;
        this.velY = 0;

        this.isMovingUp = false;
        this.isMovingDown = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }

    public void update(Camera camera) {
        boolean collideX = camera.getMap().collide(this.posX+this.velX, this.posY, Tile.SIZE-1, Tile.SIZE-1);
        boolean collideY = camera.getMap().collide(this.posX, this.posY+this.velY, Tile.SIZE-1, Tile.SIZE-1);
        boolean collide = camera.getMap().collide(this.posX+this.velX, this.posY+this.velY, Tile.SIZE-1, Tile.SIZE-1);

        if (!collideX || !collide) this.posX += this.velX;
        if (!collideY || !collide) this.posY += this.velY;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawRect(this.posX, this.posY, Tile.SIZE-1, Tile.SIZE-1);
        g.drawString("X: " + this.posX + " Y: " + this.posY, this.posX+10, this.posY+20);
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

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
}
