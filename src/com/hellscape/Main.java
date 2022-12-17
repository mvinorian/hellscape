package com.hellscape;

import javax.swing.JFrame;

import com.hellscape.asset.AssetManager;
import com.hellscape.ui.GamePanel;

public class Main {
    
    public static void main(String[] args) {
        AssetManager.load();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Test Main");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new GamePanel(1152, 640, 2));
                frame.pack();
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }
}
