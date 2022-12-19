package com.hellscape.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.character.Player;
import com.hellscape.map.MiniMap;

public class UI implements Drawable {
    
    private GamePanel gp;
    private Font maruMonica;
    private Color shaderColor;
    public Button playButton;
    public Button quitButton;
    private Player playerIcon;

    private BufferedImage bgTitle;
    private int bgTitleScreenX;

    public UI(GamePanel gp) {
        this.gp = gp;
        this.shaderColor = new Color(0, 0, 0, 0.5F);

        int y = 7*gp.tileSize/2;
        this.playButton = new Button(gp, "PLAY",(gp.screenWidth-gp.tileSize)/2, y, gp.tileSize, gp.tileSize/4);
        this.quitButton = new Button(gp, "QUIT",(gp.screenWidth-gp.tileSize)/2, y+gp.tileSize/3, gp.tileSize, gp.tileSize/4);

        this.playerIcon = new Player(gp);
        this.bgTitleScreenX = 0;

        try {
            this.maruMonica = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/MaruMonica.ttf"));
            this.bgTitle = ImageIO.read(getClass().getResourceAsStream("/state/title/background.png"));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage resizedBgTitle = new BufferedImage(2*gp.screenWidth, gp.screenHeight, bgTitle.getType());
        Graphics2D g = resizedBgTitle.createGraphics();
        g.drawImage(bgTitle, 0, 0, 2*gp.screenWidth, gp.screenHeight, null);
        this.bgTitle = resizedBgTitle;
    }

    @Override
    public void update() {
        switch (gp.gameState) {
            case GamePanel.titleState: {
                playButton.update();
                quitButton.update();
                if (playButton.state == Button.submitted) gp.gameState = GamePanel.playState;
                if (quitButton.state == Button.submitted) System.exit(0);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(maruMonica.deriveFont(Font.PLAIN, 20F));
        g.setColor(Color.WHITE);

        switch (gp.gameState) {
            case GamePanel.titleState: {
                this.drawTitleState(g);
                break;
            }
            case GamePanel.playState: {
                this.drawPlayScreen(g);
                break;
            }
            case GamePanel.pauseState: {
                this.drawPlayScreen(g);
                this.drawPauseScreen(g);
                break;
            }
            case GamePanel.endState: {
                this.drawEndScreen(g);
                break;
            }
        }
    }

    public void drawTitleState(Graphics2D g) {
        g.drawImage(bgTitle, bgTitleScreenX, 0 , null);
        if (bgTitleScreenX-- < -gp.screenWidth) bgTitleScreenX = 0;

        g.setFont(g.getFont().deriveFont(Font.BOLD, 128F));
        String text = "HELLSCAPE";
        int x = (gp.screenWidth-getLength(g, text))/2;
        int y = 5*gp.tileSize/4;
        drawShadedText(g, text, x, y, 5, 5);

        playButton.draw(g);
        quitButton.draw(g);
        playerIcon.draw(g);
    }

    public void drawPlayScreen(Graphics2D g) {
        g.setFont(g.getFont().deriveFont(Font.BOLD, 32F));
        String text = String.format("%d/%d F", gp.world.floorCount, gp.world.maxFloor);
        int x = gp.screenWidth - 10 - (gp.world.maxWorldCol*MiniMap.unit+getLength(g, text))/2;
        int y = gp.world.maxWorldRow*MiniMap.unit + 40;
        g.drawString(text, x, y);
    }

    public void drawPauseScreen(Graphics2D g) {
        g.setColor(shaderColor);
        g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 80F));
        String text = "PAUSED";
        int x = (gp.screenWidth-getLength(g, text))/2;
        int y = gp.screenHeight/2;
        g.drawString(text, x, y);
    }

    public void drawEndScreen(Graphics2D g) {
        g.setColor(shaderColor);
        g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 80F));
        String text = "YOU WIN";
        int x = (gp.screenWidth-getLength(g, text))/2;
        int y = gp.screenHeight/2;
        g.drawString(text, x, y);
    }

    private void drawShadedText(Graphics2D g, String text, int x, int y, int offsetX, int offsetY) {
        g.setColor(shaderColor);
        g.drawString(text, x+offsetX, y+offsetY);

        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }

    private int getLength(Graphics2D g, String text) {
        return (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
    }
}
