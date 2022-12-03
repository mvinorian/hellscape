package com.hellscape.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.hellscape.asset.PlayerSprite;
import com.hellscape.control.Camera;
import com.hellscape.util.*;

public class Player implements KeyListener {

    public static final int SIZE = 160;
    
    private Box box;
    private int speed;

    private int velX;
    private int velY;
    private static int spriteCount = 0;
	private static int spriteType = 0;

    private boolean isMovingUp;
    private boolean isMovingDown;
    private boolean isMovingLeft;
    private boolean isMovingRight;
    private static boolean isLastRight = false;

    public Player(Point pos, int speed) {
    	PlayerSprite.load();
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
        this.box.translate(this.velX, 0);
        if (camera.getMap().isCollide(this.box)) this.box.translate(-this.velX, 0);
        
        this.box.translate(0, this.velY);
        if (camera.getMap().isCollide(this.box)) this.box.translate(0, -this.velY);
    }

    public void draw(Graphics2D g) {
        spriteCount++;
        if(spriteCount ==  30) {
        	if(spriteType < 3) {
        		spriteType++;
        	}
        	else {
        		spriteType = 0;
        	}
        	spriteCount = 0;
        }
        if (!this.isMovingDown && !this.isMovingLeft && !this.isMovingRight && !this.isMovingUp) {
        	PlayerSprite.drawSpriteIdle(g, this.box, spriteType, isLastRight);
        }
        else PlayerSprite.drawSpriteRun(g, this.box, spriteType, isLastRight);
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
                isLastRight = false;
                break;
            case 'd':
                this.velX = this.speed;
                this.isMovingRight = true;
                isLastRight = true;
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
                if (this.isMovingRight) {
                	this.velX = this.speed;
                	isLastRight = true;
                }
                this.isMovingLeft = false;
                break;
            case 'd':
                this.velX = 0;
                if (this.isMovingLeft) {
                	this.velX = -this.speed;
                	isLastRight = false;
                }
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
