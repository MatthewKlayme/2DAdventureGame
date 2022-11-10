package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import tile.TileManager;
import entity.Player;


public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTileSize = 16;//16x16 tile (default size of player character sprites)
    //we need to scale because screen resolution is much higher
    final int scale = 3; //so now its still 16x16 but it looks like 48x48
    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize*maxScreenCol; //768 pixels width
    public final int screenHeight = tileSize*maxScreenRow; //576 pixels height


    // WORLD SETTING
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int fps = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //Allows game to have frames and do things by frames
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);
    

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);//passing the entire GamePanel Class into this Thread
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/fps; //were using nano seconds so every 1billion nanosecond(1 second) divided by fps (60) to update that many times every nanosecond
        double nextDrawTime = System.nanoTime() + drawInterval; //checks system time, and everytime it increases by 1 frame or 1/60 it will know when to redraw the screen

        while(gameThread != null){

            //1 UPDATE: Such as character / enemy position
            update();

            //2 DRAW: draw new updated information
            repaint();//for some reason this is how you call the paint component method

            try{//makes sure the game runs at 60pfs and sleeps if anything more than 60fps has happened so the animation dont go at insane speeds
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }

    public void update(){//updates the games x time per frame
        player.update();
    }
    public void paintComponent(Graphics g){//This is a built in function from Java-called by repaint()//draws updated information
        super.paintComponent(g);//super is because its calling the parent class which is JPanel
        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose(); //frees up space after drawing whats needed
    }
    
}
