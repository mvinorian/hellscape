package com.hellscape.ui;

import java.awt.Graphics2D;

import com.hellscape.control.Camera;
import com.hellscape.map.Map;
import com.hellscape.util.Box;

public class StatusBar {
    
    private MiniMap minimap;
    
    public StatusBar(Camera camera) {
        this.minimap = new MiniMap(camera.getMap().getGrid(),
                       new Box(camera.getWidth()-4*Map.WIDTH, 0,
                               4*Map.WIDTH, 4*Map.HEIGHT));
    }

    public void update(Camera camera) {
        this.minimap.update(camera);
    }

    public void draw(Graphics2D g) {
        this.minimap.draw(g);
    }
}
