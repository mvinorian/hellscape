package com.hellscape.asset;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.character.Enemy;
import com.hellscape.ui.GamePanel;
import com.hellscape.util.Box;
import com.hellscape.util.Point;

public class EnemySprite {

    public static final int TOTAL_FRAME = 8;
    
    private static BufferedImage idle, move, attack;
    
    public static void load() {
        try {
            idle = ImageIO.read(new File("assets/slime/slime-idle.png"));
            move = ImageIO.read(new File("assets/slime/slime-move.png"));
            attack = ImageIO.read(new File("assets/slime/slime-attack.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void draw(Graphics2D g, Box dst, int frame, int state, int face) {
        BufferedImage sprite = idle;
        switch (state) {
            case Enemy.STATE_IDLE:
                sprite = idle;
                break;
            case Enemy.STATE_MOVE:
                sprite = move;
                break;
            case Enemy.STATE_ATTACK:
                sprite = attack;
                break;
        }
        if (sprite == null) return;
        
        frame = frame * TOTAL_FRAME / GamePanel.REFRESH_RATE;

        Point s1 = new Point(frame*Enemy.SPRITE_SIZE, face*Enemy.SPRITE_SIZE);
        Point s2 = new Point((frame+1)*Enemy.SPRITE_SIZE, (face+1)*Enemy.SPRITE_SIZE);
        Point d1 = dst.getPoint();
        Point d2 = new Point(d1);
        d2.translate(dst.getWidth(), dst.getHeight());

        g.drawImage(sprite,
            d1.x, d1.y, d2.x, d2.y,
            s1.x, s1.y, s2.x, s2.y, null);
    }
}
