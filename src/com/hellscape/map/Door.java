package com.hellscape.map;

import java.awt.Graphics2D;

import com.hellscape.asset.ObjectSprite;
import com.hellscape.character.Enemy;
import com.hellscape.control.Camera;
import com.hellscape.util.Box;
import com.hellscape.util.Point;

public class Door {

    public static final int WIDTH = 3*Tile.TILE_SIZE;
    public static final int HEIGHT = 2*Tile.TILE_SIZE;
    
    private Box box;
    private Box cBox;
    private Box hBox;
    private int state;
    private boolean onCamera;
    private boolean entered;

    public Door(Point pos) {
        this.box = new Box(pos, WIDTH, HEIGHT);
        this.box.translate(-Tile.TILE_SIZE, 0);
        this.state = ObjectSprite.DOOR_CLOSED;
        this.setState(state);
        this.onCamera = false;
        this.entered = false;
    }

    public void update(Camera camera) {
        this.entered = false;
        this.onCamera = camera.getCamBox().isCollide(this.box);
        if (Enemy.getEnemyCount() == 0) this.setState(ObjectSprite.DOOR_OPEN);
        if (camera.getPlayer().isCollide(this.hBox) == true) this.entered = true;
    }

    public void draw(Graphics2D g) {
        if (this.onCamera == false) return;
        ObjectSprite.draw(g, this.box, state);
    }

    public boolean isCollide(Box box) {
        return this.cBox.isCollide(box);
    }

    public boolean isEntered() {
        return this.entered;
    }

    private void setState(int state) {
        this.state = state;
        switch (state) {
            case ObjectSprite.DOOR_CLOSED:
                this.cBox = new Box(box);
                this.cBox.setPadding(3*HEIGHT/4, WIDTH/4, HEIGHT/16, WIDTH/4);
                this.hBox = new Box(box);
                this.hBox.resize(0, 0);
                break;
            case ObjectSprite.DOOR_OPEN:
                this.cBox = new Box(box);
                this.cBox.resize(0, 0);
                this.hBox = new Box(box);
                this.hBox.setPadding(3*HEIGHT/4, WIDTH/4, HEIGHT/16, WIDTH/4);
                break;
        }
    }
}
