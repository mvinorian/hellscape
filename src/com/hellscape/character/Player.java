package com.hellscape.character;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.hellscape.asset.PlayerSprite;
import com.hellscape.control.Camera;
import com.hellscape.util.*;

public class Player implements KeyListener {

    public static final int SIZE = 160;
    
    private Box box;
    private Box cBox;
    private int speed;

    private int velX;
    private int velY;
    private static int spriteCount = 0;
	private static int spriteType = 0;

    private boolean isMovingUp;
    private boolean isMovingDown;
    private boolean isMovingLeft;
    private boolean isMovingRight;
    private boolean isLastRight;

    public Player(Point pos, int speed) {
    	PlayerSprite.load();
        this.box = new Box(pos, SIZE, SIZE);
        this.cBox = new Box(box);
        this.cBox.translate(SIZE/4, 2*SIZE/3);
        this.cBox.resize(SIZE/2, SIZE/3);
        this.speed = speed;

        this.velX = 0;
        this.velY = 0;

        this.isMovingUp = false;
        this.isMovingDown = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
        this.isLastRight = false;
    }

    public void update(Camera camera) {
        this.box.translate(this.velX, 0);
        this.cBox.translate(this.velX, 0);
        if (camera.getMap().isCollide(this.cBox)) {
            this.box.translate(-this.velX, 0);
            this.cBox.translate(-this.velX, 0);
        }
        
        this.box.translate(0, this.velY);
        this.cBox.translate(0, this.velY);
        if (camera.getMap().isCollide(this.cBox)) {
            this.box.translate(0, -this.velY);
            this.cBox.translate(0, -this.velY);
        }
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
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                this.velY = -this.speed;
                this.isMovingUp = true;
                break;
            case KeyEvent.VK_S:
                this.velY = this.speed;
                this.isMovingDown = true;
                break;
            case KeyEvent.VK_A:
                this.velX = -this.speed;
                this.isMovingLeft = true;
                isLastRight = false;
                break;
            case KeyEvent.VK_D:
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
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                this.velY = 0;
                if (this.isMovingDown) this.velY = this.speed;
                this.isMovingUp = false;
                break;
            case KeyEvent.VK_S:
                this.velY = 0;
                if (this.isMovingUp) this.velY = -this.speed;
                this.isMovingDown = false;
                break;
            case KeyEvent.VK_A:
                this.velX = 0;
                if (this.isMovingRight) {
                	this.velX = this.speed;
                	isLastRight = true;
                }
                this.isMovingLeft = false;
                break;
            case KeyEvent.VK_D:
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
