package com.hellscape.character;

import java.awt.Graphics2D;

import com.hellscape.ui.*;
import com.hellscape.util.Box;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;

    public boolean isMovingUp;
    public boolean isMovingDown;
    public boolean isMovingLeft;
    public boolean isMovingRight;

    public Player(GamePanel gp) {
        super(gp);
        this.loadSprite("/enemy/slime");
        this.screenX = (gp.screenWidth - gp.tileSize)/2;
        this.screenY = (gp.screenHeight - gp.tileSize)/2;

        this.worldX = gp.world.worldStart.x*gp.tileSize;
        this.worldY = gp.world.worldStart.y*gp.tileSize;
        this.speed = 2;

        this.cBox = new Box(worldX, worldY, gp.tileSize, gp.tileSize);
        this.cBox.setPadding(3*gp.tileSize/4, gp.tileSize/4, 0, gp.tileSize/4);

        this.isMovingUp = false;
        this.isMovingDown = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }
    
    @Override
    public void update() {
        if (velX == 0 && velY == 0) {
            if (this.state != idle) this.frameCount = 0;
            this.state = idle;
        } else {
            if (this.state != move) this.frameCount = 0;
            this.state = move;
        }
        this.translate(velX, 0);
        if (gp.world.isCollide(cBox) == true) this.translate(-velX, 0);

        this.translate(0, velY);
        if (gp.world.isCollide(cBox) == true) this.translate(0, -velY);
    }

    @Override
    public void draw(Graphics2D g) {
        this.frameCount = (frameCount+1) % gp.refreshRate;
        int frame = frameCount * maxFrame / gp.refreshRate;
        g.drawImage(sprite[state][direction][frame], screenX, screenY, null);
        g.drawRect(
            cBox.getX() - worldX + screenX, 
            cBox.getY() - worldY + screenY, 
            cBox.getWidth(), cBox.getHeight()
        );
    }

    public void move(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.cBox = new Box(worldX, worldY, gp.tileSize, gp.tileSize);
        this.cBox.setPadding(3*gp.tileSize/4, gp.tileSize/4, 0, gp.tileSize/4);
    }
}
