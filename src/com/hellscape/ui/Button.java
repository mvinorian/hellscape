package com.hellscape.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Button implements Drawable {

    public static final int normal = 0;
    public static final int hovered = 1;
    public static final int clicked = 2;
    public static final int submitted = 3;

    private final int screenX;
    private final int screenY;
    private final int width;
    private final int height;

    private GamePanel gp;
    private String text;
    private Color bgColor;
    private Color shaderColor;

    public int state;

    public Button(GamePanel gp, String text, int screenX, int screenY, int width, int height) {
        this.gp = gp;
        this.screenX = screenX;
        this.screenY = screenY;
        this.width = width;
        this.height = height;
        this.text = text;
        this.state = normal;
        // this.bgColor = new Color(135, 133, 121);
        this.bgColor = new Color(114, 117, 27);
        this.shaderColor = new Color(0, 0, 0, 0.25F);
    }

    @Override
    public void update() {
        if (gp.mouseH.mouseX < screenX || gp.mouseH.mouseX > screenX+width
        || gp.mouseH.mouseY < screenY || gp.mouseH.mouseY > screenY+height) {
            this.state = normal;
            return;
        }

        if (state == clicked && gp.mouseH.mouseReleased == true) this.state = submitted;
        else if (gp.mouseH.mousePressed == true) this.state = clicked;
        else this.state = hovered;        
    }

    @Override
    public void draw(Graphics2D g) {
        if (state == submitted) System.out.println("submitted");
        g.setStroke(new BasicStroke(3));
        g.setColor(shaderColor);
        g.drawRoundRect(screenX+2, screenY+2, width, height, 5, 5);
        
        if (state == normal) g.setColor(bgColor);
        else if (state == hovered) g.setColor(bgColor.darker());
        else if (state == clicked) g.setColor(bgColor.darker().darker());
        g.fillRoundRect(screenX, screenY, width, height, 5, 5);

        g.setColor(Color.WHITE);
        g.drawRoundRect(screenX, screenY, width, height, 5, 5);

        g.setFont(g.getFont().deriveFont(Font.BOLD, (height-5F)));
        int x = screenX + (width-(int)g.getFontMetrics().getStringBounds(text, g).getWidth())/2;
        int y = screenY + height - 5;

        g.setColor(shaderColor);
        g.drawString(text, x+2, y+2);

        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }
}
