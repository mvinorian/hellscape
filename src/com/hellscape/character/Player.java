package com.hellscape.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.hellscape.control.Camera;
import com.hellscape.util.*;

public class Player implements KeyListener {

    public static final int SIZE = 160;
    
    private Box box;
    private int speed;

    private int velX;
    private int velY;

    private boolean isMovingUp;
    private boolean isMovingDown;
    private boolean isMovingLeft;
    private boolean isMovingRight;

    public Player(Point pos, int speed) {
        this.box = new Box(pos, SIZE, SIZE);
        this.speed = speed;

        this.velX = 0;
        this.velY = 0;

        this.isMovingUp = false;
        this.isMovingDown = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }

    public void update(Camera camera) {
        Box boxX = new Box(box);
        Box boxY = new Box(box);
        Box boxXY = new Box(box);
        boxX.translate(this.velX, 0);
        boxY.translate(0, this.velY);
        boxXY.translate(this.velX, this.velY);

        boolean isCollide = camera.getMap().isCollide(boxXY);

        if (camera.getMap().isCollide(boxX) == false || !isCollide) this.box.translate(this.velX, 0);
        if (camera.getMap().isCollide(boxY) == false || !isCollide) this.box.translate(0, this.velY);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawRect(this.box.getX(), this.box.getY(), this.box.getWidth(), this.box.getHeight());
        g.drawString("X: " + this.box.getX() + " Y: " + this.box.getY(), this.box.getX()+10, this.box.getY()+20);
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

    public Point getPos() {
        return this.box.getPoint();
    }

    public int getPosX() {
        return this.box.getX();
    }

    public int getPosY() {
        return this.box.getY();
    }
}
