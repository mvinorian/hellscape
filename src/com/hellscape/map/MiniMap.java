package com.hellscape.map;

import java.awt.Color;
import java.awt.Graphics2D;

import com.hellscape.ui.Drawable;
import com.hellscape.ui.GamePanel;

public class MiniMap implements Drawable {
    
    private final int unit = 3;

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

    public void reset() {
        this.playerWorldCol = gp.player.cBox.getX()/gp.tileSize;
        this.playerWorldRow = gp.player.cBox.getY()/gp.tileSize;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        for (int col = 0; col < gp.world.maxWorldCol; col++)
        for (int row = 0; row < gp.world.maxWorldRow; row++) {
            if (gp.world.world[row][col].isPassable == false) continue;
            g.fillRect(screenX+col*unit, screenY+row*unit, unit, unit);
        }
        g.setColor(Color.BLUE);
        g.fillRect(
            screenX+playerWorldCol*unit, 
            screenY+playerWorldRow*unit, 
            unit, unit
        );
    }
}
