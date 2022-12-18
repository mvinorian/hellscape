package com.hellscape.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W: {
                gp.player.velY = -gp.player.speed;
                gp.player.isMovingUp = true;
                if (gp.player.isMovingDown) gp.player.velY = 0;
                break;
            }
            case KeyEvent.VK_S: {
                gp.player.velY = gp.player.speed;
                gp.player.isMovingDown = true;
                if (gp.player.isMovingUp) gp.player.velY = 0;
                break;
            }
            case KeyEvent.VK_A: {
                gp.player.velX = -gp.player.speed;
                gp.player.direction = gp.player.faceLeft;
                gp.player.isMovingLeft = true;
                if (gp.player.isMovingRight) gp.player.velX = 0;
                break;
            }
            case KeyEvent.VK_D: {
                gp.player.velX = gp.player.speed;
                gp.player.direction = gp.player.faceRight;
                gp.player.isMovingRight = true;
                if (gp.player.isMovingLeft) gp.player.velX = 0;
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W: {
                gp.player.velY = 0;
                gp.player.isMovingUp = false;
                if (gp.player.isMovingDown) gp.player.velY = gp.player.speed;
                break;
            }
            case KeyEvent.VK_S: {
                gp.player.velY = 0;
                gp.player.isMovingDown = false;
                if (gp.player.isMovingUp) gp.player.velY = -gp.player.speed;
                break;
            }
            case KeyEvent.VK_A: {
                gp.player.velX = 0;
                gp.player.isMovingLeft = false;
                if (gp.player.isMovingRight) {
                    gp.player.direction = gp.player.faceRight;
                    gp.player.velX = gp.player.speed;
                }
                break;
            }
            case KeyEvent.VK_D: {
                gp.player.velX = 0;
                gp.player.isMovingRight = false;
                if (gp.player.isMovingLeft) {
                    gp.player.direction = gp.player.faceLeft;
                    gp.player.velX = -gp.player.speed;
                }
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }    
}
