package com.hellscape.state;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.character.Player;
import com.hellscape.sound.Sound;
import com.hellscape.ui.Button;
import com.hellscape.ui.GamePanel;

public class TitleState extends State {

    private Button playButton;
    private Button quitButton;
    private Player playerIcon;

    private BufferedImage bg;
    private int bgScreenX;

    public TitleState(GamePanel gp) {
        super(gp);

        int width = 3*gp.tileSize/2;
        int height = gp.tileSize/4;
        int x = (gp.screenWidth-width)/2;
        int y = 13*gp.tileSize/4;
        int yOffset = 3*height/2;
        
        this.playButton = new Button(gp, "PLAY", x, y+=yOffset, width, height);
        this.quitButton = new Button(gp, "QUIT", x, y+=yOffset, width, height);
        this.playerIcon = new Player(gp);
        this.bgScreenX = 0;

        try {
            this.bg = ImageIO.read(getClass().getResourceAsStream("/state/title/background.png"));
            BufferedImage resizedBg = new BufferedImage(2*gp.screenWidth, gp.screenHeight, bg.getType());
            Graphics2D g = resizedBg.createGraphics();
            g.drawImage(bg, 0, 0, 2*gp.screenWidth, gp.screenHeight, null);
            this.bg = resizedBg;
            g.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        playerIcon.update();
        playButton.update();
        quitButton.update();
        if (playButton.state == Button.submitted) {
            gp.gameState = GamePanel.playState;
            gp.bgm.playLoop(Sound.bgmBattle);
        }
        if (quitButton.state == Button.submitted) System.exit(0);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(bg, bgScreenX, 0 , null);
        if (bgScreenX-- < -gp.screenWidth) bgScreenX = 0;

        g.setFont(g.getFont().deriveFont(Font.BOLD, 128F));
        String text = "HELLSCAPE";
        int x = (gp.screenWidth-getLength(g, text))/2;
        int y = 5*gp.tileSize/4;
        drawShadedText(g, text, x, y, 5, 5);

        playButton.draw(g);
        quitButton.draw(g);
        playerIcon.draw(g);
    }

    public void reset() {
        this.playerIcon = new Player(gp);
    }
}
