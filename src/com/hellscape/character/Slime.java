package com.hellscape.character;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.hellscape.sound.Sound;
import com.hellscape.ui.GamePanel;
import com.hellscape.util.Box;

public class Slime extends Entity {

    private final int offCamera = 0;
    private final int background = 1;
    private final int foreground = 2;

    private int screenX, screenY;
    private Color healthBarColor;
    private BasicStroke healthBarStroke;
    private int zPos;
    
    public Slime(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.loadSprite("/enemy/slime");
        this.healthBarColor = new Color(204, 63, 85);
        this.healthBarStroke = new BasicStroke(gp.scale);

        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = 1;
        
        this.hBox = new Box(worldX, worldY, gp.tileSize, gp.tileSize);
        this.cBox = new Box(worldX, worldY, gp.tileSize, gp.tileSize);
        this.hBox.setPadding(gp.tileSize/4, 3*gp.tileSize/16, 0, 3*gp.tileSize/16);
        this.cBox.setPadding(3*gp.tileSize/4, 3*gp.tileSize/16, 0, 3*gp.tileSize/16);

        this.maxLife = 10*gp.world.floorCount;
        this.life = maxLife;
        this.attack = 2*gp.world.floorCount;

        this.zPos = offCamera;
    }

    @Override
    public void update() {
        if (isDead() == true) {
            if (zPos != offCamera) gp.enemies.remove(this);
            if (zPos == foreground) gp.foreground.remove(this);
            if (zPos == background) gp.background.remove(this);
            return;
        }
        this.frameCount = (frameCount+1) % gp.refreshRate;
        if (this.state == attackState && this.frameCount == gp.refreshRate-1) {
            gp.player.getHit(this.attack);
        }
        if (this.updateZPos() == false) return;
        if (this.updateVel() == false) return;
        this.updateWorldPos();
    }

    @Override
    public void draw(Graphics2D g) {
        if (isDead() == true) return;
        super.draw(g);
        int frame = frameCount * maxFrame / gp.refreshRate;

        this.drawHealthBar(g);
        g.drawImage(sprite[state][direction][frame], screenX, screenY, null);
    }

    public boolean isDead() {
        return this.life <= 0;
    }

    public void getHit(int attack) {
        this.life -= attack;
        gp.sfx.play(Sound.sfxHitMonster);
    }

    private void drawHealthBar(Graphics2D g) {
        int width = life*(gp.tileSize/2)/maxLife;
        int height = 3*gp.scale;
        int x = screenX + gp.tileSize/4;
        int y = screenY;

        g.setColor(healthBarColor);
        g.fillRect(x, y, width, height);
        g.setColor(healthBarColor.brighter());
        g.fillRect(x, y, width, height/2);
        g.setColor(Color.BLACK);
        g.setStroke(healthBarStroke);
        g.drawRect(x, y, gp.tileSize/2, height);
    }

    private boolean updateVel() {
        int dX = gp.player.worldX - this.worldX;
        int dY = gp.player.worldY - this.worldY;

        if (this.state == attackState && this.frameCount != 0) return false;

        if (dX*dX + dY*dY < gp.tileSize*gp.tileSize) {
            if (this.state != attackState) this.frameCount = 0;
            this.state = attackState;
            return false;
        }

        if (dX > gp.screenWidth/2 || dY > gp.screenHeight/2) {
            if (this.state != idleState) this.frameCount = 0;
            this.state = idleState;
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
            if (this.state != moveState) this.frameCount = 0;
            this.state = moveState;
        }
        else {
            if (this.state != idleState) this.frameCount = 0;
            this.state = idleState;
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
