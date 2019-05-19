package GamePackage;

import javax.swing.JFrame;

public class BlockBreaker {

    public static void main(String[] args) {
       JFrame obj = new JFrame();
       Gameplay gamePlay = new Gameplay();
       
       int frameWidth = 700;
       int frameHeight = 600;
       obj.setBounds(200, 20, frameWidth, frameHeight);
       obj.setTitle("Ball");
       obj.setResizable(false);
       obj.setVisible(true);
       obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       obj.add(gamePlay);
       //set_visible here if doesn't show
    }
}
