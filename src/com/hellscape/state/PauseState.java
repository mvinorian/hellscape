package com.hellscape.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.ui.Button;
import com.hellscape.ui.GamePanel;

public class PauseState extends State {
    
    private Button resumeButton;
    private Button homeButton;
    private Button quitButton;

    private BufferedImage bg;

    public PauseState(GamePanel gp) {
        super(gp);
        int width = 3*gp.tileSize/2;
        int height = gp.tileSize/4;
        int x = (gp.screenWidth-width)/2;
        int y = 9*gp.tileSize/4;
        int yOffset = 3*height/2;
        
        this.resumeButton = new Button(gp, "RESUME", x, y+=yOffset, width, height);
        this.homeButton = new Button(gp, "MAIN MENU", x, y+=yOffset, width, height);
        this.quitButton = new Button(gp, "QUIT", x, y+=yOffset, width, height);

        try {
            this.bg = ImageIO.read(getClass().getResourceAsStream("/state/pause/background.png"));
            BufferedImage resizedBg = new BufferedImage(gp.screenWidth, gp.screenHeight, bg.getType());
            Graphics2D g = resizedBg.createGraphics();
            g.drawImage(bg, 0, 0, gp.screenWidth, gp.screenHeight, null);
            this.bg = resizedBg;
            g.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        resumeButton.update();
        homeButton.update();
        quitButton.update();
        if (resumeButton.state == Button.submitted) gp.gameState = GamePanel.playState;
        if (homeButton.state == Button.submitted) gp.reset();
        if (quitButton.state == Button.submitted) System.exit(0);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(shaderColor);
        g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g.drawImage(bg, 0, 0, null);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 80F));
        String text = "PAUSED";
        int x = (gp.screenWidth-getLength(g, text))/2;
        int y = 2*gp.tileSize;
        drawShadedText(g, text, x, y, 3, 3);

        resumeButton.draw(g);
        homeButton.draw(g);
        quitButton.draw(g);
    }
}
