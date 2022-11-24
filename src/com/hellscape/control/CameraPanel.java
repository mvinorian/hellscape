package com.hellscape.control;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

public class CameraPanel extends JPanel {

    public static final int REFRESH_RATE = 120;

    private int width;
    private int height;
    
    private Camera camera;

    public CameraPanel(int width, int height, int speed) {
        this.camera = new Camera(width, height, speed);

        this.width = width;
        this.height = height;

        this.setPreferredSize(new Dimension(this.width, this.height));
        this.addKeyListener(this.camera.getPlayer());
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension dim = e.getComponent().getSize();
                camera.resize(dim.width, dim.height);
            }
        });
        this.setFocusable(true);
        this.startThread();
    }

    public void startThread() {
        Thread gameThread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    camera.update();
                    repaint();

                    try {
                        Thread.sleep(1000 / REFRESH_RATE);
                    } catch (InterruptedException e) {}
                }
            }
        };
        gameThread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        camera.render(g2d);
    }
}
