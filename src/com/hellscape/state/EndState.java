package com.hellscape.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.hellscape.ui.GamePanel;

public class EndState extends State {

    public EndState(GamePanel gp) {
        super(gp);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(shaderColor);
        g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 80F));
        String text = (gp.player.life == 0) ? "YOU LOSE" : "YOU WIN";
        int x = (gp.screenWidth-getLength(g, text))/2;
        int y = gp.screenHeight/2;
        g.drawString(text, x, y);
    }
}
