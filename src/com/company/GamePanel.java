package com.company;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 750;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20;
    static final int GAME_OBJECTS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static int DELAY;
    int[] x = new int[GAME_OBJECTS];
    int[] y = new int[GAME_OBJECTS];
    int bodyPartIncrement;
    int bodyParts = 1;
    int appleX;
    int appleY;
    int score = 0;
    String level = "";
    int hiscore;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    static boolean isPaused = false;
    private BufferedImage pause, play;
    boolean isHiscoreBroken = false;

    public GamePanel(int delay, int bodyPartIncrement, String level){
        DELAY = delay;
        this.level = level;
        hiscore = FileHandling.readingFromFile( level + ".txt");
        random = new Random();
        try {
            pause = ImageIO.read(new File("pause.jpg"));
            play = ImageIO.read(new File("play.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bodyPartIncrement = bodyPartIncrement;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseListener(new MyMouseAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void move(){
        if (!isPaused){
            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction) {
                case 'R' -> x[0] = x[0] + UNIT_SIZE;
                case 'L' -> x[0] = x[0] - UNIT_SIZE;
                case 'U' -> y[0] = y[0] - UNIT_SIZE;
                case 'D' -> y[0] = y[0] + UNIT_SIZE;
            }
        }
    }
    public void draw(Graphics g){
        g.setColor(new Color(0, 175, 105));
        g.setFont(new Font("MS Reference Sans serif", Font.BOLD, 35));
        g.drawString("Score: " + score, 400, 400);
        if (!isHiscoreBroken) {
            g.drawString("Hiscore: " + hiscore, 400, 450);
        }
        else {
            g.drawString("Hiscore: " + hiscore, 400, 450);
            g.setFont(new Font("MS Reference Sans serif", Font.BOLD, 20));
            g.drawString("(broken!)", 400, 470);
        }

        g.setColor(new Color(150, 0, 0));
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        for (int i=0; i<bodyParts; i++){
            if (i==0){
                g.setColor(new Color(73, 247, 10));
                g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, 10, 10);
            }
            else{
                g.setColor(new Color(146, 242, 36));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }

        if (isPaused){
            g.drawImage(pause, 0, 0, this);
        }
        else {
            g.drawImage(play, 0, 0, this);
        }

        if (!running){
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)((SCREEN_WIDTH-80)/UNIT_SIZE))*UNIT_SIZE + 40;
        appleY = random.nextInt((int)((SCREEN_HEIGHT-80)/UNIT_SIZE))*UNIT_SIZE + 40;
    }
    public void detectCollision(){
        for (int i = bodyParts; i > 0; i--){
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        if (x[0] == 0){
            running = false;
        }
        if(y[0] < 0){
            running = false;
        }
        if (x[0] + UNIT_SIZE > SCREEN_WIDTH){
            running = false;
        }
        if(y[0] + UNIT_SIZE > SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        if (!(WelcomePanel.isMute)){
            try {
                FileHandling.sound("Crash .wav");
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
        g.setColor(new Color(250, 75, 0));
        g.setFont(new Font("MS Reference Sans serif", Font.BOLD, 75));
        g.drawString("Game Over!", 115, 100);
        g.setFont(new Font("MS Reference Sans serif", Font.BOLD, 25));
        g.drawString("Press Enter to Exit...", 115, 140);
        g.drawString("Press SpaceBar to Play Again...", 115, 180);
    }
    public void checkApple(){
        if((appleX==x[0]) && (appleY==y[0])){
            if (!(WelcomePanel.isMute)){
                try {
                    FileHandling.sound("Beep Short .wav");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }
            }
            bodyParts += bodyPartIncrement;
            score += 10;
            newApple();
        }
    }
    public void checkHighScore(){
        if (hiscore < score){
            hiscore = score;
            isHiscoreBroken = true;
            FileHandling.writingToFile(level + ".txt", hiscore);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            detectCollision();
            checkHighScore();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    if (!isPaused) {
                        direction = 'U';
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (!isPaused) {
                        direction = 'D';
                    }
                }
                case KeyEvent.VK_LEFT -> {
                    if (!isPaused) {
                        direction = 'L';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (!isPaused) {
                        direction = 'R';
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (!running) {
                        exit();
                    }
                }
                case KeyEvent.VK_SPACE -> {
                    if (!running) {
                        restart();
                    } else {
                        isPaused = !isPaused;
                    }
                }
            }
        }
    }

    public class MyMouseAdapter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            int mouseX = e.getX();
            int mouseY = e.getY();
            if (((mouseX >= 20) && (mouseX <= 65)) && ((mouseY >= 20) && (mouseY <= 65))){
                isPaused = !isPaused;
            }
        }
    }

    public void restart(){
        x = new int[GAME_OBJECTS];
        y = new int[GAME_OBJECTS];
        bodyParts = 1;
        score = 0;
        direction = 'R';
        isPaused = false;
        isHiscoreBroken = false;
        startGame();
    }

    public void exit(){
        Main.welcome.setFocusable(true);
        WelcomePanel.gameFrame.dispose();
    }
}
