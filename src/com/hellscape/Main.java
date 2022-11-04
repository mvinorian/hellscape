package com.hellscape;

import javax.swing.JFrame;

import com.hellscape.control.CameraPanel;

public class Main {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Test Main");

                System.out.println("in mainThread");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new CameraPanel(Main.WIDTH, Main.HEIGHT, 2));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
    
}
