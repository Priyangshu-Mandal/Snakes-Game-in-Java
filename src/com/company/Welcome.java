package com.company;

import javax.swing.*;
import java.awt.*;

public class Welcome extends JFrame {
    public Welcome(){
        this.add(new WelcomePanel());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Welcome to Snakes");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        this.setIconImage(icon);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
