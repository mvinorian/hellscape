package com.hellscape.character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.hellscape.ui.Drawable;
import com.hellscape.ui.GamePanel;
import com.hellscape.util.Box;

public class Projectile implements Drawable{
	
	final int speed = 6;
	final int spriteSize = 6;
	final int size;
	final int damage = 4;
	
	protected GamePanel gp;
    private int worldX, worldY;
    private int screenX, screenY;
    public Box cBox;
    public double velX, velY;
    private int exploding = 0;
    public boolean done;
    
    protected BufferedImage sprite;
	
	public Projectile (GamePanel gp) {
		size = spriteSize*gp.scale;
		this.gp = gp;
		this.worldX = gp.player.worldX + (gp.tileSize-size)/2;
		this.worldY = gp.player.worldY + (gp.tileSize-size)/2;
		
		double dX = gp.mouseH.mouseX - gp.screenWidth/2;
		double dY = gp.mouseH.mouseY - gp.screenHeight/2;
		double angle = Math.atan2(dY, dX);
		velX = speed * Math.cos(angle);
		velY = speed * Math.sin(angle);
		
		this.cBox = new Box(worldX, worldY, size, size);
		this.cBox.setPadding(size/6, size/6, size/6, size/6);
		exploding = 0;
		done = false;
	}
	
	@Override
	public void update() {
		if (exploding > 0) {
			return;
		}
		if (gp.world.isCollide(cBox)) {
			done = true;
			return;
		}
		for (Entity enemy : gp.enemies) {
			if(enemy.isCollide(cBox)) {
				enemy.getHit(damage);
				done = true;
				return;
			}
		}
		worldX += Math.round(velX);
		worldY += Math.round(velY);
		
		cBox.translate((int)Math.round(velX), (int)Math.round(velY));
		
		
		this.screenX = worldX - gp.player.worldX + gp.player.screenX;
        this.screenY = worldY - gp.player.worldY + gp.player.screenY;

	}

	@Override
	public void draw(Graphics2D g) {
		if(done) return;
		
		g.drawImage(gp.player.bulletImg, screenX, screenY, null);
//		g.drawRect(cBox.getX() - gp.player.worldX + gp.player.screenX, cBox.getY() - gp.player.worldY + gp.player.screenY, cBox.getWidth(), cBox.getHeight());
	}
}
