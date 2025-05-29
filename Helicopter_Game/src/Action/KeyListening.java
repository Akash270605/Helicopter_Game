/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Action;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import GamePlay.Helicopter;

public class KeyListening implements KeyListener{
    public static Helicopter helicopter;
    
    public void keyPressed(KeyEvent e){
        helicopter.backward = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        helicopter.backward = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e){
        
    }
}
