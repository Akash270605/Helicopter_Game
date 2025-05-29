/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GamePlay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Map {
    
    public static BufferedImage map1;
    public static BufferedImage map2;
    public static BufferedImage map3;
    public static BufferedImage gameOver;
    public static int level;
    private static boolean rocketON;
    private static boolean firstChange;
    
    public Map(){
        level =0;
        rocketON = false;
        firstChange = false;
        
        File imageFile = new File("src/Resources/MapLevel1.png");
        
        try{
            map1 = ImageIO.read(imageFile);
        }
        catch(IOException e){
            System.err.println("Error reading to level 1!");
            e.printStackTrace();
        }
        
        imageFile = new File("src/Resources/MapLevel2.png");
        
        try{
            map2= ImageIO.read(imageFile);
        }
        catch(IOException e){
            System.err.println("Error reading to level 2!");
            e.printStackTrace();
        }
        
        imageFile= new File("src/Resources/MapLevel3.png");
        
        try{
            map3 = ImageIO.read(imageFile);
        }
        catch(IOException e){
            System.err.println("Error reading to level 3!");
            e.printStackTrace();
        }
        
        imageFile = new File("src/Resources/GameOver.png");
        
        try{
            gameOver = ImageIO.read(imageFile);
        }
        catch(IOException e){
            System.err.println("Error reading GameOver String");
            e.printStackTrace();
        }
    }
    
    public void setLevel(int level){
        this.level = level;
    }
    
    public int getLevel(){
        return this.level;
    }
    
    public void setBonus(String mode){
        if(mode == "ON") this.firstChange = true;
        else if(mode == "OFF") this.firstChange= false;
    }
    
    public boolean getBonusState(){
        return this.rocketON;
    }
    
    public void setMapChange(String mode){
        if(mode == "ON") this.firstChange = true;
        else if(mode == "OFF") this.firstChange = false;
    }
    
    public boolean getMapChange(){
        return this.firstChange;
    }
}
