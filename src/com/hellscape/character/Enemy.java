package com.hellscape.character;

import java.awt.Graphics2D;

import com.hellscape.asset.EnemySprite;
import com.hellscape.ui.Camera;
import com.hellscape.ui.GamePanel;
import com.hellscape.util.*;

public class Enemy {

    public static final int BOX_SIZE = 128;
    public static final int SPRITE_SIZE = 32;
    public static final int STATE_IDLE = 1;
    public static final int STATE_MOVE = 2;
    public static final int STATE_ATTACK = 3;
    public static final int FACE_RIGHT = 0;
    public static final int FACE_LEFT = 1;

    private static int enemyCount = 0;
    
    private Box box;
    private Box cBox;
    private Box hBox;
    private int frame;
    private int state;
    private int face;
    private boolean onCamera;
    private boolean isDead;

    public Enemy(Point pos) {
        this.box = new Box(pos, BOX_SIZE, BOX_SIZE);
        this.cBox = new Box(this.box);
        this.hBox = new Box(this.box);
        this.cBox.setPadding(3*BOX_SIZE/4, 3*BOX_SIZE/16, 0, 3*BOX_SIZE/16);
        this.hBox.setPadding(BOX_SIZE/4, 3*BOX_SIZE/16, 0, 3*BOX_SIZE/16);
        this.state = STATE_IDLE;
        this.onCamera = false;
        this.isDead = false;
        enemyCount++;
    }

    public void update(Camera camera) {
        if (this.isDead == true) return;

        this.onCamera = this.box.isCollide(camera.getCamBox());
        if (this.onCamera == false) return;
        Point dst = camera.getPlayer().getPos();
        int dX = dst.x - this.box.getX();
        int dY = dst.y - this.box.getY();

        if (dX*dX + dY*dY <= 10000) {
            this.state = STATE_ATTACK;
            return;
        }
        
        if (dX*dX + dY*dY >= 409600) {
            this.state = STATE_IDLE;
            return;
        }
        this.face = (Integer.signum(dX) == 1) ? FACE_RIGHT : FACE_LEFT;
        
        int velX = Integer.signum(dX);
        int velY = Integer.signum(dY);

        boolean moveX = (velX != 0);
        this.translate(velX, 0);
        if (camera.getMap().isCollide(this.cBox)) {
            this.translate(-velX, 0);
            moveX = false;
        }

        boolean moveY = (velY != 0);
        this.translate(0, velY);
        if (camera.getMap().isCollide(this.cBox)) {
            this.translate(0, -velY);
            moveY = false;
        }

        if (moveX || moveY) this.state = STATE_MOVE;
        else this.state = STATE_IDLE;

        if (this.hBox.isCollide(camera.getPlayer().getBox())) {
            this.isDead = true;
            enemyCount--;
        }
    }

    public void draw(Graphics2D g) {
        if (this.isDead == true) return;
        if (this.onCamera == false) return;

        this.frame = (this.frame+1) % GamePanel.REFRESH_RATE;
        EnemySprite.draw(g, this.box, this.frame, this.state, this.face);
        // g.drawRect(this.hBox.getX(), this.hBox.getY(), this.hBox.getWidth(), this.hBox.getHeight());
        // g.drawRect(this.cBox.getX(), this.cBox.getY(), this.cBox.getWidth(), this.cBox.getHeight());
    }

    private void translate(int dX, int dY) {
        this.box.translate(dX, dY);
        this.cBox.translate(dX, dY);
        this.hBox.translate(dX, dY);
    }

    public boolean isCollide(Box box) {
        if (this.isDead == true) return false;
        return this.cBox.isCollide(box);
    }
    
    public boolean isAbove(Box box) {
        if (this.isDead == true) return false;
        return this.box.isCollide(box) && box.getY() < this.cBox.getY();
    }

    public boolean isDead() {
        return this.isDead;
    }

    public Box getBox() {
        if (this.isDead == true) return new Box(0, 0, 0, 0);
        return this.box;
    }

    public static int getEnemyCount() {
        return enemyCount;
    }
}
