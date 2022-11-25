package com.hellscape.asset;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.util.*;

public class Tileset {
    
    private static BufferedImage tileset;

    public static void load() {
        try {
            tileset = ImageIO.read(new File("assets/heaven-tileset.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void draw(Graphics2D g, Box dst, Box src) {
        if (tileset == null) return;
        
        Point d1 = dst.getPoint();
        Point s1 = src.getPoint();
        Point d2 = new Point(d1);
        Point s2 = new Point(s1);
        d2.translate(dst.getWidth(), dst.getHeight());
        s2.translate(src.getWidth(), src.getHeight());

        g.drawImage(tileset,
            d1.x, d1.y, d2.x, d2.y,
            s1.x, s1.y, s2.x, s2.y, null);
    }
}
