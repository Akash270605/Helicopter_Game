/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Action;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import GamePlay.Helicopter;

public class MouseListening implements MouseListener{
    private static Helicopter helicopter;
    
    public MouseListening(Helicopter helicopter){
        this.helicopter = helicopter;
    }
    
    public void mouseClicked(MouseEvent e){
        
    }
    
    public void mouseEntered(MouseEvent e){
        
    }
    
    public void mouseExited(MouseEvent e){
        
    }
    
    public void mousePressed(MouseEvent e){
        helicopter.setState("asc");
    }
    
    public void mouseReleased(MouseEvent e){
        helicopter.setState("desc");
    }
}
