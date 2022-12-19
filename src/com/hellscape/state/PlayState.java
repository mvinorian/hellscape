package com.hellscape.state;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.map.MiniMap;
import com.hellscape.ui.GamePanel;

public class PlayState extends State {

    private BufferedImage heart;
    private Color healthBarColor;
    private final int healthBarWidth = 64;

    public PlayState(GamePanel gp) {
        super(gp);
        this.healthBarColor = new Color(204, 63, 85);
        
        try {
            this.heart = ImageIO.read(getClass().getResourceAsStream("/decoration/heart.png"));
            BufferedImage resizedHeart = new BufferedImage(gp.tileSize, gp.tileSize, heart.getType());
            Graphics2D gHeart = resizedHeart.createGraphics();
            gHeart.drawImage(heart, 0, 0, gp.tileSize, gp.tileSize, null);
            this.heart = resizedHeart;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(g.getFont().deriveFont(Font.BOLD, 32F));
        String text = String.format("%d/%d F", gp.world.floorCount, gp.world.maxFloor);
        int x = gp.screenWidth - 10 - (gp.world.maxWorldCol*MiniMap.unit+getLength(g, text))/2;
        int y = gp.world.maxWorldRow*MiniMap.unit + 40;
        g.drawString(text, x, y);

        x = 48;
        y = 24;
        int width = gp.player.life*healthBarWidth/gp.player.maxLife;
        int height = 4;
        g.setColor(gp.getBackground());
        g.fillRoundRect(x, y-height/2, (2+healthBarWidth)*gp.scale, (height+1)*gp.scale, height*gp.scale, height*gp.scale);
        g.setColor(gp.getBackground().brighter());
        g.fillRoundRect(x, y-height/2, (2+healthBarWidth)*gp.scale, (height+1)*gp.scale/2, height*gp.scale/2, height*gp.scale/2);
        g.setColor(healthBarColor);
        g.fillRoundRect(x, y, (2+width)*gp.scale, height*gp.scale, height*gp.scale/2, height*gp.scale/2);
        g.setColor(healthBarColor.brighter());
        g.fillRoundRect(x, y, (2+width)*gp.scale, height*gp.scale/2, height*gp.scale/4, height*gp.scale/4);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(height));
        g.drawRoundRect(x, y-height/2, (2+healthBarWidth)*gp.scale, (height+1)*gp.scale, height*gp.scale, height*gp.scale);
        g.drawImage(heart, 0, 0, null);
    }
}
