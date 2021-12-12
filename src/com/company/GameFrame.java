package com.company;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame(int delay, int bodyPArtIncrement, String level){
        this.add(new GamePanel(delay, bodyPArtIncrement, level));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Snakes");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        this.setIconImage(icon);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
