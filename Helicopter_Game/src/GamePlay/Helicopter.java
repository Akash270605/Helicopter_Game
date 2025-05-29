/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GamePlay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Helicopter {
    
    public static BufferedImage helicopterAscending;
    public static BufferedImage helicopterDescending;
    public static BufferedImage helicopterCrashed;
    private static int xPosition;
    private int yPosition;
    private static int score;
    private static boolean crash;
    private static boolean ascending;
    public boolean backward;
    
    public Helicopter(){
        backward = false;
        score = 0;
        xPosition= 150;
        yPosition = 150;
        crash = false;
        ascending = false;
        
        File imageFile = new File("src/Resources/HelicopterAsc.png");
        
        try{
            helicopterAscending= ImageIO.read(imageFile);
        }
        catch(IOException e){
            System.err.println("Error Helicopter ascending reading ");
            e.printStackTrace();
        }
        
        imageFile = new File("src/Resources/HelicopterDesc.png");
        
        try{
            helicopterDescending= ImageIO.read(imageFile);
        }
        catch(IOException e){
            System.err.println("Error Helicopter Descending reading");
            e.printStackTrace();
        }
        
        imageFile = new File("src/Resources/HelicopterCrashed.png");
        
        try{
            helicopterCrashed = ImageIO.read(imageFile);
        }
        catch(IOException e){
            System.err.println("Error crashed reading");
            e.printStackTrace();
        }
    }
    
    public void addScore(int score){
        this.score += score;
    }
    
    public void setState(String state){
       if(state == "crash") this.crash = true;
        else if(state == "asc") this.ascending = true;
        else if(state == "desc") this.ascending = false;
    }
    
    public void setYUp(int y){
        this.yPosition += y;
    }
    
    public void setYDown(int y){
        this.yPosition -= y;
    }
    
    public void setXUp(int x){
        this.xPosition += x;
    }
    
    public int getY(){
        return this.yPosition;
    }
    
    public int getX(){
        return this.xPosition;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public boolean getCrashState(){
        return this.crash;
    }
    
    public boolean getFlyState(){
        return this.ascending;
    }
}
