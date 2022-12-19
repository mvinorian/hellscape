package com.hellscape.character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.hellscape.sound.Sound;
import com.hellscape.ui.*;
import com.hellscape.util.Box;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;

    public boolean isMovingUp;
    public boolean isMovingDown;
    public boolean isMovingLeft;
    public boolean isMovingRight;
    public BufferedImage bulletImg;
    public List<Projectile> projectiles;
    public int projectileCount;
    private int cdCount = 0;

    public Player(GamePanel gp) {
        super(gp);
        this.loadSprite("/player/shooter");
        this.screenX = (gp.screenWidth - gp.tileSize)/2;
        this.screenY = (gp.screenHeight - gp.tileSize)/2;

        this.worldX = gp.world.worldStart.x*gp.tileSize;
        this.worldY = gp.world.worldStart.y*gp.tileSize;
        this.speed = 2;

        this.cBox = new Box(worldX, worldY, gp.tileSize, gp.tileSize);
        this.cBox.setPadding(3*gp.tileSize/4, gp.tileSize/4, 0, gp.tileSize/4);

        this.maxLife = 100;
        this.life = maxLife;

        this.isMovingUp = false;
        this.isMovingDown = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
        
        this.projectiles = new ArrayList<Projectile>();
        this.projectileCount = 0;
        
        this.loadProjectile();
    }

	@Override
    public void update() {
        this.frameCount = (frameCount+1) % gp.refreshRate;
        if (this.life == 0) {
            gp.gameState = GamePanel.endState;
        }
        if (velX == 0 && velY == 0) {
            if (this.state != idleState) this.frameCount = 0;
            this.state = idleState;
        } else {
            if (this.state != moveState) this.frameCount = 0;
            this.state = moveState;
        }
        this.translate(velX, 0);
        if (gp.world.isCollide(cBox) == true) this.translate(-velX, 0);

        this.translate(0, velY);
        if (gp.world.isCollide(cBox) == true) this.translate(0, -velY);
        
        updateAttack();
        
        if(cdCount > 0) cdCount--;
        
        for (int i = 0; i < projectiles.size(); i++) {
        	if(projectiles.get(i).done) {
        		projectiles.remove(i);
        		continue;
        	}
        	projectiles.get(i).update();
        }
        
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        int frame = frameCount * maxFrame / gp.refreshRate;
        g.drawImage(sprite[state][direction][frame], screenX, screenY, null);
        for (int i = 0; i < projectiles.size(); i++) {
        	projectiles.get(i).draw(g);
        }
        // g.drawRect(
        //     cBox.getX() - worldX + screenX, 
        //     cBox.getY() - worldY + screenY, 
        //     cBox.getWidth(), cBox.getHeight()
        // );
    }

    public void getHit(int attack) {
        this.life -= attack;
        gp.sfx.play(Sound.sfxGetHit);
    }

    public void setPosition() {
        this.worldX = gp.world.worldStart.x*gp.tileSize;
        this.worldY = gp.world.worldStart.y*gp.tileSize;
        this.cBox = new Box(worldX, worldY, gp.tileSize, gp.tileSize);
        this.cBox.setPadding(3*gp.tileSize/4, gp.tileSize/4, 0, gp.tileSize/4);
    }

    public void reset() {
        this.frameCount = 0;
        this.state = idleState;
        this.direction = faceRight;
        
        this.worldX = gp.world.worldStart.x*gp.tileSize;
        this.worldY = gp.world.worldStart.y*gp.tileSize;
        this.speed = 2;

        this.cBox = new Box(worldX, worldY, gp.tileSize, gp.tileSize);
        this.cBox.setPadding(3*gp.tileSize/4, gp.tileSize/4, 0, gp.tileSize/4);

        this.maxLife = 100;
        this.life = maxLife;

        this.isMovingUp = false;
        this.isMovingDown = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }
    
    private void updateAttack() {
    	if (gp.mouseH.mousePressed == false) return;
    	if (cdCount > 0) return;
    	projectiles.add(new Projectile(gp));
    	cdCount = 60;
    }
    
    private void loadProjectile() {
    	try {
			bulletImg = ImageIO.read(getClass().getResourceAsStream("/projectile/BulletPrototype.png"));
			BufferedImage resizedBullet = new BufferedImage(6*gp.scale, 6*gp.scale, bulletImg.getType());
            Graphics2D g = resizedBullet.createGraphics();
            g.drawImage(bulletImg, 0, 0, 6*gp.scale, 6*gp.scale, null);
            bulletImg = resizedBullet;
            g.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
	}
}
