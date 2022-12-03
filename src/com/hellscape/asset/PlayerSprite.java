package com.hellscape.asset;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.util.Box;

public class PlayerSprite {
	
	private static BufferedImage idle, idle0, idle1, idle2, idle3, idle4, idle5, idle6, idle7;
	private static BufferedImage run, run0, run1, run2, run3, run4, run5, run6, run7;
	
	
	public static void load() {
		try {
			
			idle0 = ImageIO.read(new File("assets/Assassin/Idle_Cut/idle_cut0.png"));
			idle1 = ImageIO.read(new File("assets/Assassin/Idle_Cut/idle_cut1.png"));
			idle2 = ImageIO.read(new File("assets/Assassin/Idle_Cut/idle_cut2.png"));
			idle3 = ImageIO.read(new File("assets/Assassin/Idle_Cut/idle_cut3.png"));
			
			idle4 = ImageIO.read(new File("assets/Assassin/Idle_Cut/idle_cut4.png"));
			idle5 = ImageIO.read(new File("assets/Assassin/Idle_Cut/idle_cut5.png"));
			idle6 = ImageIO.read(new File("assets/Assassin/Idle_Cut/idle_cut6.png"));
			idle7 = ImageIO.read(new File("assets/Assassin/Idle_Cut/idle_cut7.png"));
			
			run0 = ImageIO.read(new File("assets/Assassin/Run_Cut/run_cut0.png"));
			run1 = ImageIO.read(new File("assets/Assassin/Run_Cut/run_cut1.png"));
			run2 = ImageIO.read(new File("assets/Assassin/Run_Cut/run_cut2.png"));
			run3 = ImageIO.read(new File("assets/Assassin/Run_Cut/run_cut3.png"));
			
			run4 = ImageIO.read(new File("assets/Assassin/Run_Cut/run_cut4.png"));
			run5 = ImageIO.read(new File("assets/Assassin/Run_Cut/run_cut5.png"));
			run6 = ImageIO.read(new File("assets/Assassin/Run_Cut/run_cut6.png"));
			run7 = ImageIO.read(new File("assets/Assassin/Run_Cut/run_cut7.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void drawSpriteIdle(Graphics2D g, Box dst, int spriteType, boolean mirror) {
		switch(spriteType) {
			case 0:
				if(mirror) idle = idle4;
				else idle = idle0;
				break;
			case 1:
				if(mirror) idle = idle5;
				else idle = idle1;
				break;
			case 2:
				if(mirror) idle = idle6;
				else idle = idle2;
				break;
			default:
				if(mirror) idle = idle7;
				else idle = idle3;
		}
		g.drawImage(idle, dst.getX(), dst.getY(), dst.getWidth(), dst.getHeight(), null);
    }
	
	public static void drawSpriteRun(Graphics2D g, Box dst, int spriteType, boolean mirror) {
		switch(spriteType) {
			case 0:
				if(mirror) run = run4;
				else run = run0;
				break;
			case 1:
				if(mirror) run = run5;
				else run = run1;
				break;
			case 2:
				if(mirror) run = run6;
				else run = run2;
				break;
			default:
				if(mirror) run = run7;
				else run = run3;
		}
		g.drawImage(run, dst.getX(), dst.getY(), dst.getWidth(), dst.getHeight(), null);
    }
}
