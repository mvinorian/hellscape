package com.hellscape.state;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hellscape.character.Player;
import com.hellscape.ui.Button;
import com.hellscape.ui.GamePanel;

public class TitleState extends State {

    private Button playButton;
    private Button quitButton;
    private Player playerIcon;

    private BufferedImage bgTitle;
    private int bgTitleScreenX;

    public TitleState(GamePanel gp) {
        super(gp);
        int y = 7*gp.tileSize/2;
        this.playButton = new Button(gp, "PLAY",(gp.screenWidth-gp.tileSize)/2, y, gp.tileSize, gp.tileSize/4);
        this.quitButton = new Button(gp, "QUIT",(gp.screenWidth-gp.tileSize)/2, y+gp.tileSize/3, gp.tileSize, gp.tileSize/4);
        this.playerIcon = new Player(gp);
        this.bgTitleScreenX = 0;

        try {
            this.bgTitle = ImageIO.read(getClass().getResourceAsStream("/state/title/background.png"));
            BufferedImage resizedBgTitle = new BufferedImage(2*gp.screenWidth, gp.screenHeight, bgTitle.getType());
            Graphics2D gBgTitle = resizedBgTitle.createGraphics();
            gBgTitle.drawImage(bgTitle, 0, 0, 2*gp.screenWidth, gp.screenHeight, null);
            this.bgTitle = resizedBgTitle;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {
        playButton.update();
        quitButton.update();
        if (playButton.state == Button.submitted) gp.gameState = GamePanel.playState;
        if (quitButton.state == Button.submitted) System.exit(0);
    }

    @Override
    public void draw(Graphics2D g) {
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
}
