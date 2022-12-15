package com.hellscape;

import javax.swing.JFrame;

import com.hellscape.control.CameraPanel;

public class Main {
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Test Main");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new CameraPanel(800, 480, 3));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
