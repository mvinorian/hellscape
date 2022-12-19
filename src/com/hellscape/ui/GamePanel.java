package com.hellscape.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.hellscape.character.Entity;
import com.hellscape.character.Player;
import com.hellscape.map.Map;
import com.hellscape.map.MiniMap;

public class GamePanel extends JPanel implements Runnable {
    
    public final int spriteSize = 32;
    public final int scale = 4;
    public final int tileSize = spriteSize*scale;

    public final int maxTileRow = 5;
    public final int maxTileCol = 9;
    public final int screenWidth = maxTileCol*tileSize;
    public final int screenHeight = maxTileRow*tileSize;

    public final int maxState = 4;
    public static final int titleState = 0;
    public static final int playState = 1;
    public static final int pauseState = 2;
    public static final int endState = 3;

    public final int refreshRate = 120;

    public Thread thread;
    public KeyHandler keyH;
    public MouseHandler mouseH;
    public int gameState;

    public Player player;
    public Map world;
    public MiniMap miniMap;
    public List<Entity> enemies;
    public UI ui;

    public List<Drawable> background;
    public List<Drawable> foreground;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth , screenHeight));
        this.setupGame();

        this.keyH = new KeyHandler(this);
        this.mouseH = new MouseHandler(this);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.setBackground(new Color(135, 133, 121));
        this.setFocusable(true);
    }
    
    private void setupGame() {
        this.enemies = new ArrayList<Entity>();
        this.background = new ArrayList<Drawable>();
        this.foreground = new ArrayList<Drawable>();

        this.world = new Map(this);
        this.world.generate();
        this.player = new Player(this);
        this.world.generateEnemies();
        this.miniMap = new MiniMap(this, screenWidth-MiniMap.unit*world.maxWorldCol-10, 10);
        this.ui = new UI(this);
        this.gameState = titleState;
    }

    public void startThread() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        double delta = 0;
        double drawInterval = 1e9 / refreshRate;
        long lastTime = System.nanoTime();
        long currentTime;

        while (this.thread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime-lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                this.repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (gameState != playState) this.ui.update();
        else {
            this.player.update();
            this.world.update();
            this.miniMap.update();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.update();

        Graphics2D g2d = (Graphics2D) g;

        if (gameState == titleState) {
            ui.draw(g2d);
            g2d.dispose();
            return;
        }

        this.world.draw(g2d);
        for (Drawable obj : background) obj.draw(g2d);
        this.player.draw(g2d);
        for (Drawable obj : foreground) obj.draw(g2d);

        this.ui.draw(g2d);

        g2d.dispose();
    }
    
    public void nextFloor() {
        this.world.generate();
        this.player.setPosition();
        this.world.generateEnemies();
        this.miniMap.set();
        this.world.floorCount++;
    }

    public void reset() {
        this.enemies.clear();
        this.background.clear();
        this.foreground.clear();
        this.world.reset();
        this.world.generate();
        this.player.reset();
        this.world.generateEnemies();
        this.miniMap.reset();
        this.ui.reset();
        this.gameState = titleState;
    }
}
