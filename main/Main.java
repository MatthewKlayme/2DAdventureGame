package main;
import javax.swing.JFrame;

public class Main {
    public static void main(String[]args){
        JFrame window = new JFrame(); //window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes when you hit the X
        window.setResizable(false); //cant rezise the window
        window.setTitle("Java Game"); //title

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null); // makes window in the center
        window.setVisible(true);//so we can actually see the window

        gamePanel.startGameThread();//calls the game to open and run

    }
}