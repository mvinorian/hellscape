package com.hellscape.character;

import java.awt.Graphics2D;

import com.hellscape.ui.GamePanel;
import com.hellscape.util.Box;

public class Slime extends Entity {

    private final int offCamera = 0;
    private final int background = 1;
    private final int foreground = 2;

    private int screenX, screenY;
    private int zPos;
    
    public boolean isDead;
    
    public Slime(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.loadSprite("/enemy/slime");

        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = 1;
        
        this.hBox = new Box(worldX, worldY, gp.tileSize, gp.tileSize);
        this.cBox = new Box(worldX, worldY, gp.tileSize, gp.tileSize);
        this.hBox.setPadding(gp.tileSize/4, 3*gp.tileSize/16, 0, 3*gp.tileSize/16);
        this.cBox.setPadding(3*gp.tileSize/4, 3*gp.tileSize/16, 0, 3*gp.tileSize/16);

        this.zPos = offCamera;
    }

    @Override
    public void update() {
        if (gp.player.isCollide(hBox)) {
            isDead = true;
            if (zPos != offCamera) gp.enemies.remove(this);
            if (zPos == foreground) gp.foreground.remove(this);
            if (zPos == background) gp.background.remove(this);
            return;
        }
        if (this.updateZPos() == false) return;
        if (this.updateVel() == false) return;
        this.updateWorldPos();
    }

    @Override
    public void draw(Graphics2D g) {
        if (isDead) return;
        super.draw(g);
        this.frameCount = (frameCount+1) % gp.refreshRate;
        int frame = frameCount * maxFrame / gp.refreshRate;

        g.drawImage(sprite[state][direction][frame], screenX, screenY, null);
    }

    private boolean updateVel() {
        int dX = gp.player.worldX - this.worldX;
        int dY = gp.player.worldY - this.worldY;

        if (dX*dX + dY*dY < 10000) {
            if (this.state != attack) this.frameCount = 0;
            this.state = attack;
            return false;
        }

        if (dX > 4*gp.tileSize || dY > 2*gp.tileSize) {
            if (this.state != idle) this.frameCount = 0;
            this.state = idle;
            return false;
        }

        this.direction = (Integer.signum(dX) == 1) ? faceRight : faceLeft;
        this.velX = Integer.signum(dX) * speed;
        this.velY = Integer.signum(dY) * speed;

        return true;
    }

    private void updateWorldPos() {
        boolean onCollision = false;
        boolean isMovingX = (velX != 0);
        boolean isMovingY = (velY != 0);

        this.translate(velX, 0);
        onCollision = gp.world.isCollide(cBox);
        for (Entity enemy : gp.enemies) if (enemy != this) onCollision |= enemy.isCollide(cBox);
        if (onCollision) {
            this.translate(-velX, 0);
            isMovingX = false;
        }
        
        this.translate(0, velY);
        onCollision = gp.world.isCollide(cBox);
        for (Entity enemy : gp.enemies) if (enemy != this) onCollision |= enemy.isCollide(cBox);
        if (onCollision) {
            this.translate(0, -velY);
            isMovingY = false;
        }

        if (isMovingX || isMovingY) {
            if (this.state != move) this.frameCount = 0;
            this.state = move;
        }
        else {
            if (this.state != idle) this.frameCount = 0;
            this.state = idle;
        }
    }

    private boolean updateZPos() {
        this.screenX = worldX - gp.player.worldX + gp.player.screenX;
        this.screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (screenX < -gp.tileSize || screenX > gp.screenWidth + gp.tileSize
         || screenY < -gp.tileSize || screenY > gp.screenHeight + gp.tileSize) {
            if (zPos != offCamera) gp.enemies.remove(this);
            if (zPos == foreground) gp.foreground.remove(this);
            if (zPos == background) gp.background.remove(this);
            this.zPos = offCamera;
            return false;
        }
        if (this.zPos == offCamera) gp.enemies.add(this);

        if (this.cBox.getY() > gp.player.cBox.getY()) {
            if (this.zPos != foreground) gp.foreground.add(0, this);
            if (this.zPos == background) gp.background.remove(this);
            this.zPos = foreground;
        } else {
            if (this.zPos != background) gp.background.add(this);
            if (this.zPos == foreground) gp.foreground.remove(this);
            this.zPos = background;
        }
        return true;
    }
}
