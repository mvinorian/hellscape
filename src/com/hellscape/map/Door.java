package com.hellscape.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.ui.Drawable;
import com.hellscape.ui.GamePanel;
import com.hellscape.util.Box;

public class Door implements Drawable {

    public final int tileWidth = 3;
    public final int tileHeight = 2;
    public final int width;
    public final int height;
    private final int maxType = 2;
    private final int closed = 0;
    private final int open = 1;
    private final int background = 1;
    private final int foreground = 2;

    private GamePanel gp;
    private int worldX, worldY;
    private int screenX, screenY;
    private int zPos;

    public int state;
    public Box cBox;
    private BufferedImage[] sprite;

    public Door(GamePanel gp) {
        this.gp = gp;
        this.width = tileWidth*gp.tileSize;
        this.height = tileHeight*gp.tileSize;

        this.worldX = (gp.world.worldEnd.x-1)*gp.tileSize;
        this.worldY = gp.world.worldEnd.y*gp.tileSize;

        this.cBox = new Box(this.worldX, this.worldY, width, height);
        this.cBox.setPadding(3*height/4, width/4, height/16, width/4);
        
        this.state = closed;
        this.sprite = new BufferedImage[maxType];
        this.loadSprite("/decoration/door.png");
    }

    @Override
    public void update() {
        this.updateZPos();
        if (state == open && gp.player.isCollide(cBox)) {
            gp.reset();
            return;
        }
        if (gp.world.enemies.isEmpty()) {
            this.state = open;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(sprite[state], screenX, screenY, null);
    }

    public boolean isCollide(Box box) {
        if (state == open) return false;
        return this.cBox.isCollide(box);
    }

    public void reset() {
        this.worldX = (gp.world.worldEnd.x-1)*gp.tileSize;
        this.worldY = gp.world.worldEnd.y*gp.tileSize;
        this.cBox = new Box(this.worldX, this.worldY, width, height);
        this.cBox.setPadding(3*height/4, width/4, height/16, width/4);
        this.state = closed;
    }

    private void updateZPos() {
        this.screenX = worldX - gp.player.worldX + gp.player.screenX;
        this.screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (this.cBox.getY() > gp.player.cBox.getY()) {
            if (this.zPos != foreground) gp.foreground.add(0, this);
            if (this.zPos == background) gp.background.remove(this);
            this.zPos = foreground;
        } else {
            if (this.zPos != background) gp.background.add(this);
            if (this.zPos == foreground) gp.foreground.remove(this);
            this.zPos = background;
        }
    }
    
    private void loadSprite(String path) {
        try {
            BufferedImage sprite = ImageIO.read(getClass().getResource(path));
            for (int type = 0; type < maxType; type++) {
                this.sprite[type] = new BufferedImage(width, height, sprite.getType());
                Graphics2D g = this.sprite[type].createGraphics();

                g.drawImage(
                    sprite, 0, 0, width, height,
                    type * tileWidth * gp.spriteSize, 0,
                    (type+1) * tileWidth * gp.spriteSize,
                    tileHeight * gp.spriteSize, null
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
