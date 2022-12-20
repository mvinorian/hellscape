package com.hellscape.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.hellscape.character.Entity;
import com.hellscape.ui.Drawable;
import com.hellscape.ui.GamePanel;

public class MiniMap implements Drawable {
    
    public static final int unit = 3;

    private GamePanel gp;
    private int screenX, screenY;
    private int playerWorldRow, playerWorldCol;

    public MiniMap(GamePanel gp, int screenX, int screenY) {
        this.gp = gp;
        this.screenX = screenX;
        this.screenY = screenY;
        this.playerWorldCol = gp.player.cBox.getX()/gp.tileSize;
        this.playerWorldRow = gp.player.cBox.getY()/gp.tileSize;
        gp.foreground.add(this);
    }
    
    @Override
    public void update() {
        this.playerWorldCol = gp.player.cBox.getX()/gp.tileSize;
        this.playerWorldRow = gp.player.cBox.getY()/gp.tileSize;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(4));
        g.drawRoundRect(screenX, screenY, gp.world.maxWorldCol*unit, gp.world.maxWorldRow*unit, gp.spriteSize/2, gp.spriteSize/2);
        for (int col = 0; col < gp.world.maxWorldCol; col++)
        for (int row = 0; row < gp.world.maxWorldRow; row++) {
            if (gp.world.world[row][col].isPassable == false) continue;
            g.fillRect(screenX+col*unit, screenY+row*unit, unit, unit);
        }
        g.setColor(Color.RED);
        for (Entity enemy : gp.world.enemies) {
            int enemyWorldCol = enemy.cBox.getX()/gp.tileSize;
            int enemyWorldRow = enemy.cBox.getY()/gp.tileSize;
            g.fillRect(
                screenX+enemyWorldCol*unit,
                screenY+enemyWorldRow*unit,
                unit, unit
            );
        }
        g.setColor(Color.BLUE);
        g.fillRect(
            screenX+playerWorldCol*unit, 
            screenY+playerWorldRow*unit, 
            unit, unit
        );
    }
    
    public void set() {
        this.playerWorldCol = gp.player.cBox.getX()/gp.tileSize;
        this.playerWorldRow = gp.player.cBox.getY()/gp.tileSize;
    }

    public void reset() {
        this.playerWorldCol = gp.player.cBox.getX()/gp.tileSize;
        this.playerWorldRow = gp.player.cBox.getY()/gp.tileSize;
        gp.foreground.add(this);
    }
}
