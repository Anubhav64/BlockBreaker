package GamePackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int rows, int cols) {
        map = new int[rows][cols];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = 1;
//                if(Math.random() < 0.8 || i + j == 0){
//                    map[i][j] = 1;
//                }
            }
        }
        
        brickWidth = 540/cols;
        brickHeight = 150/rows;
    }
    public void draw(Graphics2D g) {
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] > 0){
                    //g.setColor(Color.white);
                    brickColor(g, i, j);
                    g.fillRect(j * brickWidth + 80, i *brickHeight + 50,
                            brickWidth, brickHeight);
                    
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i *brickHeight + 50,
                            brickWidth, brickHeight);
                }
            }
        }
    }
     
    private void brickColor(Graphics2D g, int i, int j){
        switch((i+j) % 5) {
            case 0:
                g.setColor(Color.white);
                break;
            case 1:
                g.setColor(Color.cyan);
                break;
            case 2:
                g.setColor(Color.orange);
                break;
            case 3:
                g.setColor(Color.green);
                break;
            case 4:
                g.setColor(Color.red);
                break;
        }
    }
     
    public void breakBrick(int row, int col){
         map[row][col] = 0;
    }
}
