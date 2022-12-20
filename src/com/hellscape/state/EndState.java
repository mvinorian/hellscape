package com.hellscape.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.ui.Button;
import com.hellscape.ui.GamePanel;

public class EndState extends State {

    private Button homeButton;
    private Button quitButton;

    private BufferedImage bg;

    public EndState(GamePanel gp) {
        super(gp);
        int width = 3*gp.tileSize/2;
        int height = gp.tileSize/4;
        int x = (gp.screenWidth-width)/2;
        int y = 9*gp.tileSize/4 + height;
        int yOffset = 3*height/2;
        
        this.homeButton = new Button(gp, "MAIN MENU", x, y+=yOffset, width, height);
        this.quitButton = new Button(gp, "QUIT", x, y+=yOffset, width, height);

        try {
            this.bg = ImageIO.read(getClass().getResourceAsStream("/state/menu/background.png"));
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
        homeButton.update();
        quitButton.update();
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
        String text1 = (gp.player.life <= 0) ? "GAME" : "YOU";
        String text2 = (gp.player.life <= 0) ? "OVER" : "WIN";
        int x = (gp.screenWidth-getLength(g, text1))/2;
        int y = 7*gp.tileSize/4;
        drawShadedText(g, text1, x, y, 3, 3);
        x = (gp.screenWidth-getLength(g, text2))/2;
        y += g.getFontMetrics().getHeight();
        drawShadedText(g, text2, x, y, 3, 3);

        homeButton.draw(g);
        quitButton.draw(g);
    }
}
