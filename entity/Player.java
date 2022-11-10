package entity;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){

        // worldX = gp.screenWidth/2;
        // worldY = gp.screenHeight/2;
        worldX = gp.tileSize*23;//starting position of the character
        worldY = gp.tileSize*21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/boy_right_2.png"));
            // up1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/big titty monke.png"));
            // up2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/big titty monke.png"));
            // down1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/big titty monke.png"));
            // down2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/big titty monke.png"));
            // left1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/big titty monke.png"));
            // left2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/big titty monke.png"));
            // right1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/big titty monke.png"));
            // right2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/HeroSprites/big titty monke.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
            if(keyH.upPressed == true){
                direction = "up";//for drawing the player in the up sprite
                // worldY -= speed;//for moving the player
            }
            if(keyH.downPressed == true){
                direction = "down";
                // worldY += speed;
            }
            if(keyH.leftPressed == true){
                direction = "left";
                // worldX -= speed;
            }
            if(keyH.rightPressed == true){
                direction = "right";
                // worldX += speed;
            }
            if(keyH.spacePressed == true){
                direction = "space";
            }

            //Check tile collision
            collisionON = false;
            gp.cChecker.checkTile(this);

            //If collision is false, Player can move
            if(collisionON == false){
                switch(direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                    case "space": worldY -= speed;worldX+=speed;break;
                }
            }
    
            //changes sprites based on number of sprites there are
            spriteCounter ++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }  
    }
    public void draw(Graphics2D g2){

        BufferedImage image = null;
        switch(direction){
        case "up":
            if(spriteNum == 1){
                image = up1;
            }
            if(spriteNum == 2){
                image = up2;
            }
            break;
        case "down":
        if(spriteNum == 1){
            image = down1;
        }
        if(spriteNum == 2){
            image = down2;
        }
            break;
            case "right":
        if(spriteNum == 1){
            image = right1;
        }
        if(spriteNum == 2){
            image = right2;
        }
            break;
        case "left":
        if(spriteNum == 1){
            image = left1;
        }
        if(spriteNum == 2){
            image = left2;
        }
            break;
        

        }
        // g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null); //this makes player move around the screen
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); //this makes player stay centered in the screen

    }
}
