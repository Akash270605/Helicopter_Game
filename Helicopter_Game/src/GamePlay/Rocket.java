/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GamePlay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Rocket {
    public static BufferedImage bonus;
    private static int xPosition;
    private static int yPosition;
    private static Random randPosition;
    
    public Rocket(){
        randPosition = new Random();
        xPosition = 1200;
        yPosition = randPosition.nextInt(300) + 120;
        
        File imageFile = new File("src/Resources/Bonus.png");
        
        try{
            bonus = ImageIO.read(imageFile);
        }catch(IOException e){
            System.err.println("Error reading Bonus");
            e.printStackTrace();
        }
    }
    
    public int getX(){
        return this.xPosition;
    }
    
    public int getY(){
        return this.yPosition;
    }
    
    public void setY(int y){
        this.yPosition = y;
    }
    
    public void setX(int x){
        this.xPosition = x;
    }
    
    public void setXDown(int x){
        this.xPosition -=x;
    }
}
