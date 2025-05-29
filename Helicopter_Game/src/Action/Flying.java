/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Action;

import GamePlay.Helicopter;
import GamePlay.Map;
import GamePlay.Obstackle;
import GamePlay.Rocket;
import Graphics.ActionPanel;
import Graphics.Window;

public class Flying implements Runnable{
    private static Helicopter helicopter;
    private static Obstackle obstackle;
    private static Rocket rocket;
    private static Map map;
    
    public Flying(Helicopter helicopter, Obstackle obstackle, Rocket rocket, Map map){
        this.helicopter = helicopter;
        this.obstackle = obstackle;
        this.rocket = rocket;
        this.map = map;
    }
    
    private void lastFly(){
        helicopter.setYUp(1);
        helicopter.setXUp(1);
        
        Window.actionPanel.repaint();
    }
    
    private void descend(){
        for (int i =0; i < 10; i++){
            if(i == 6) helicopter.setYUp(1);
            if( i == 7) helicopter.setYUp(1);
            else if(i == 8) helicopter.setYUp(2);
            else if(i == 9) helicopter.setYUp(2);
            
            if(helicopter.backward == true){
                obstackle.setXUp(1);
            }
            else{
                obstackle.setXDown(1);
            }
            
            if(helicopter.getCrashState()) break;
            
            Window.actionPanel.repaint();
            
            slow(2);
            
            if(map.getBonusState()) rocket.setXDown(1);
        }
    }
    
    private void ascend(){
        for(int i = 0; i < 9; i++){
            if(i == 4) helicopter.setYDown(1);
            else if(i == 5) helicopter.setYDown(1);
            else if(i == 7) helicopter.setYDown(1);
            else if(i == 8) helicopter.setYDown(2);
            
            if(helicopter.backward == true){
                obstackle.setXUp(1);
            }
            else{
                obstackle.setXDown(1);
            }
            
            if(helicopter.getCrashState()) break;
            
            Window.actionPanel.repaint();
            
            slow(2);
            
            if(map.getBonusState()) rocket.setXDown(1);
        }
    }
    
    private void slow(int miliSeconds){
        try{
            Thread.sleep(miliSeconds);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    public void run(){
        while(true){
            if(helicopter.getCrashState()){
                slow(5);
                lastFly();
            }
            else{
                if(!helicopter.getFlyState()){
                    descend();
                }
                else{
                    ascend();
                }
            }
        }
    }
}
