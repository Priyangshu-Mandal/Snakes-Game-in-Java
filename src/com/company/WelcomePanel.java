package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomePanel extends JPanel implements ActionListener {
    private BufferedImage image, sound, mute;
    static boolean isMute;
    Timer timer;
    static GameFrame gameFrame;
    public WelcomePanel(){
        try {
            image = ImageIO.read(new File("icon.jpg"));
            mute = ImageIO.read(new File("mute.jpg"));
            sound = ImageIO.read(new File("sound.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        isMute = FileHandling.readingBooleanFromFile("MuteStatus.txt");
        timer = new Timer(0, this);
        timer.start();
        this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseListener(new MyMouseAdapter());
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.drawImage(image, (GamePanel.SCREEN_WIDTH - image.getWidth())/2, 90, this);
        if (!isMute){
            g.drawImage(sound, 650, 500, this);
        }
        else {
            g.drawImage(mute, 650, 500, this);
        }
        g.setColor(new Color(9, 150, 255));
        g.setFont(new Font("copperplate gothic light", Font.BOLD, 60));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Snakes!", (GamePanel.SCREEN_WIDTH - metrics.stringWidth("Snakes!"))/2, 50);

        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("MS Reference Sans serif", Font.BOLD, 30));
        g.drawString("Select the Hardness Level: ", 115, 280);

        g.setFont(new Font("MS Reference Sans serif", Font.BOLD, 20));
        g.setColor(new Color(0, 144, 103));
        g.drawRoundRect(231, 315, 124, 33, 5, 5);
        g.drawString("\tPress 0 for  VERY EASY", 115, 340);
        g.setColor(new Color(63, 255, 0));
        g.drawRoundRect(231, 355, 66, 33, 5, 5);
        g.drawString("\tPress 1 for  EASY", 115, 380);
        g.setColor(new Color(213, 161, 13, 242));
        g.drawRoundRect(231, 395, 97, 33, 5, 5);
        g.drawString("\tPress 2 for  MEDIUM", 115, 420);
        g.setColor(new Color(241, 86, 1, 216));
        g.drawRoundRect(231, 435, 71, 33, 5, 5);
        g.drawString("\tPress 3 for  HARD", 115, 460);
        g.setColor(new Color(242, 5, 5));
        g.drawRoundRect(231, 475, 85, 33, 5, 5);
        g.drawString("\tPress 4 for  KILLER", 115, 500);

        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("MS Reference Sans serif", Font.BOLD, 30));
        g.drawString("Press Esc to Exit", 115, 580);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_0 -> gameFrame = new GameFrame(100, 1, "V_EASY");
                case KeyEvent.VK_1 -> gameFrame = new GameFrame(75, 2, "EASY");
                case KeyEvent.VK_2 -> gameFrame = new GameFrame(50, 2, "MEDIUM");
                case KeyEvent.VK_3 -> gameFrame = new GameFrame(25, 3, "HARD");
                case KeyEvent.VK_4 -> gameFrame = new GameFrame(5, 4, "KILLER");
                case KeyEvent.VK_ESCAPE -> {
                    timer.stop();
                    System.exit(0);
                }
                case KeyEvent.VK_M -> {
                    isMute = !isMute;
                    if (isMute){
                        FileHandling.writingToFile("MuteStatus.txt", 1);
                    }
                    else {
                        FileHandling.writingToFile("MuteStatus.txt", 0);
                    }
                }
            }
        }
    }

    public class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            if (((mouseX >= 650) && (mouseX <= 729)) && ((mouseY >= 500) && (mouseY <= 579))){
                isMute = !isMute;
                if (isMute){
                    FileHandling.writingToFile("MuteStatus.txt", 1);
                }
                else {
                    FileHandling.writingToFile("MuteStatus.txt", 0);
                }
            }
            if (((mouseX >= 233) && (mouseX <= 352)) && ((mouseY >= 318) && (mouseY <= 344))) {
                new GameFrame(100, 1, "V_EASY");
            }
            if (((mouseX >= 233) && (mouseX <= 296)) && ((mouseY >= 358) && (mouseY <= 384))) {
                new GameFrame(75, 2, "EASY");
            }
            if (((mouseX >= 233) && (mouseX <= 326)) && ((mouseY >= 398) && (mouseY <= 424))) {
                new GameFrame(50, 2, "MEDIUM");
            }
            if (((mouseX >= 233) && (mouseX <= 300)) && ((mouseY >= 438) && (mouseY <= 464))) {
                new GameFrame(25, 3, "HARD");
            }
            if (((mouseX >= 233) && (mouseX <= 314)) && ((mouseY >= 478) && (mouseY <= 504))) {
                new GameFrame(5, 4, "KILLER");
            }
        }
    }
}
