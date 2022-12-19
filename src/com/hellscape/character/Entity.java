package com.hellscape.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.ui.Drawable;
import com.hellscape.ui.GamePanel;
import com.hellscape.util.Box;

public abstract class Entity implements Drawable {

    protected final int maxFrame = 8;
    protected final int maxType = 3;
    protected final int maxDirection = 2;
    protected final int idleState = 0;
    protected final int moveState = 1;
    protected final int attackState = 2;
    public final int faceRight = 0;
    public final int faceLeft = 1;
    
    protected GamePanel gp;
    public int worldX, worldY;
    public Box cBox, hBox;
    public boolean collisionOn;
    public int speed;
    public int velX, velY;

    public int attack;
    public int maxLife;
    public int life;
    
    public int state;
    public int direction;
    public Color shaderColor;

    protected int frameCount;

    protected BufferedImage[][][] sprite;

    protected Entity(GamePanel gp) {
        this.gp = gp;
        this.frameCount = 0;
        this.state = idleState;
        this.direction = faceRight;
        this.sprite = new BufferedImage[maxType][maxDirection][maxFrame];
        this.shaderColor = new Color(0, 0, 0, 0.3F);

        this.worldX = 0; this.worldY = 0;
        this.velX = 0; this.velY = 0;
        this.speed = 0;
        this.attack = 1;
        this.maxLife = 10;
        this.life = 10;
        this.cBox = new Box(0, 0, 0, 0);
        this.hBox = new Box(0, 0, 0, 0);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(shaderColor);
        g.fillOval(
            cBox.getX()-gp.player.worldX+gp.player.screenX, 
            cBox.getY()+cBox.getHeight()/2-gp.player.worldY+gp.player.screenY, 
            cBox.getWidth(), cBox.getHeight()
        );
    }
    
    public boolean isCollide(Box box) {
        return this.cBox.isCollide(box);
    }

    protected void translate(int dX, int dY) {
        this.worldX += dX;
        this.worldY += dY;
        this.cBox.translate(dX, dY);
        this.hBox.translate(dX, dY);
    }
    
    protected void loadSprite(String path) {
        try {
            BufferedImage[] sprite = new BufferedImage[maxType];
            sprite[idleState] = ImageIO.read(getClass().getResourceAsStream(path + "/idle.png"));
            sprite[moveState] = ImageIO.read(getClass().getResourceAsStream(path + "/move.png"));
            sprite[attackState] = ImageIO.read(getClass().getResourceAsStream(path + "/attack.png"));

            for (int type = 0; type < maxType; type++) 
            for (int direction = 0; direction < maxDirection; direction++)
            for (int frame = 0; frame < maxFrame; frame++) {
                this.sprite[type][direction][frame] = new BufferedImage(gp.tileSize, gp.tileSize, sprite[type].getType());
                Graphics2D g = this.sprite[type][direction][frame].createGraphics();

                g.drawImage(
                    sprite[type], 0, 0, gp.tileSize, gp.tileSize,
                    frame * gp.spriteSize, direction * gp.spriteSize, 
                    (frame+1) * gp.spriteSize, (direction+1) * gp.spriteSize, null
                );
                
                g.dispose();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
