package com.hellscape.asset;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.util.Box;
import com.hellscape.util.Point;

public class ObjectSprite {

    public static final int DOOR_WIDTH = 96;
    public static final int DOOR_HEIGHT = 64;
    public static final int DOOR_CLOSED = 1;
    public static final int DOOR_OPEN = 2;
    
    private static BufferedImage object;

    public static void load() {
        try {
            object = ImageIO.read(new File("assets/tileset/heaven-object.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void draw(Graphics2D g, Box dst, int objectType) {
        if (object == null) return;

        Box src = new Box(0, 0, DOOR_WIDTH, DOOR_HEIGHT);
        switch (objectType) {
            case DOOR_CLOSED:
                break;
            case DOOR_OPEN:
                src.translate(DOOR_WIDTH, 0);
                break;
        }

        Point d1 = dst.getPoint();
        Point s1 = src.getPoint();
        Point d2 = new Point(d1);
        Point s2 = new Point(s1);
        d2.translate(dst.getWidth(), dst.getHeight());
        s2.translate(src.getWidth(), src.getHeight());

        g.drawImage(object,
            d1.x, d1.y, d2.x, d2.y,
            s1.x, s1.y, s2.x, s2.y, null);
    }
}
