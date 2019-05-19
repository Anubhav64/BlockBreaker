package GamePackage;


import java.awt.*;
import javax.swing.*;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import javax.swing.JPanel;
//import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
    // Initial setup
    private final int frameWidth = 700;
    private final int frameHeight = 600;
    
    private final int brickRows = 4;
    private final int brickCols = 5;
    private final int initBrickCount = brickRows * brickCols;
    private final int initTimeBonus = 500*brickRows*brickCols; // 4 sec per brick
    private final int initBallX = 180;
    private final int initBallY = 350;
    private final int initDirX = -2;
    private final int initDirY = -3;
    //
    
    // game
    private boolean play = false;
    private int score = 0;
    
    // map
    private int totalBricks = initBrickCount;
    
    // time
    private final Timer timer;
    private final int delay = 8;
    private int timeBonus = initTimeBonus;
    
    // sliding board
    private int playerX = 310;
    private final int boardSpeed = 4;
    private boolean boardMoveLeft = false;
    private boolean boardMoveRight = false;
    
    // ball
    private int ballPosX = initBallX;
    private int ballPosY = initBallY;
    private int ballXdir = initDirX;
    private int ballYdir = initDirY;
    
    // maybe change name
    private MapGenerator map;
    
    public Gameplay(){
        map = new MapGenerator(brickRows, brickCols);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    @Override
    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(0, 0, frameWidth-10, frameHeight-10);
        
        //drawing map
        map.draw((Graphics2D) g);
        
        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, frameHeight-10);
        g.fillRect(0, 0, frameWidth-10, 3);
        g.fillRect(frameWidth-15, 0, 3, frameHeight-10);
        
//        g.setColor(Color.yellow);
//        g.fillRect(0,0,3,592);
//        g.fillRect(0,0,692,3);
//        g.fillRect(691,0,3,592);
        
        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);
        
        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, frameHeight - 50, 100, 8);
        
        //ball
        g.setColor(Color.yellow);
        //g.drawString(""+ballXdir, 390, 30);
        
        g.setColor(Color.blue);
        if(Math.abs(ballXdir) > 3) g.setColor(Color.yellow);
        if(Math.abs(ballXdir) > 5) {
            g.setColor(Color.red);
            g.drawString("On fire!", 190, 30);
        }
        g.fillOval(ballPosX, ballPosY, 20, 20);
        
        // better to combine these ifs since mostly similar
        if(totalBricks <= 0){
            timer.stop();
            score += timeBonus/100;
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You won! Final score: "+score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to play again", 230, 360);
        }
        
        if(ballPosY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game over, final score: "+score, 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to play again", 230, 360);
        }
        
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            boardMoveLeft = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            boardMoveRight = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            } else {
                moveRight();
                boardMoveRight = true;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            } else {
                moveLeft();
                boardMoveLeft = true;
            }
        }
        
        if(!play && e.getKeyCode() == KeyEvent.VK_ENTER){
            play = true;
            ballPosX = initBallX;
            ballPosY = initBallY;
            ballXdir = initDirX;
            ballYdir = initDirY;
            playerX = 310;
            score = 0;
            totalBricks = initBrickCount;
            map = new MapGenerator(brickRows, brickCols);
            timeBonus = initTimeBonus;
            timer.start();
            
            repaint();
        }
    }
    
    public void moveRight() {
        play = true;
        playerX += boardSpeed;
    }
    
    public void moveLeft() {
        play = true;
        playerX -= boardSpeed;
    }

    

    @Override
    public void actionPerformed(ActionEvent arg0) {
        timer.start();
        if(timeBonus > 0) timeBonus--;
        if(play) {
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = (ballYdir < 0)? ballYdir : -ballYdir;
                if(boardMoveRight) ballXdir++;
                if(boardMoveLeft) ballXdir--;
            }
            
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if(ballPosX < 0){
                ballXdir = -ballXdir;
            }
            if(ballPosY < 0){
                ballYdir = -ballYdir;
            }
            if(ballPosX > 670){
                ballXdir = -ballXdir;
            }
            
            
            if(boardMoveRight) moveRight();
            if(boardMoveLeft) moveLeft();
        
            // Naming could be better
            A: for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;
                        
                        if(ballRect.intersects(brickRect)){
                            map.breakBrick(i, j);
                            totalBricks--;
                            score += 5 + Math.max(0, 2*Math.abs(ballXdir) - 10);
                            
                            if(ballPosX + 19 <= brickRect.x || 
                                    ballPosX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = - ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
        }
        
        repaint();
    }
    
}
