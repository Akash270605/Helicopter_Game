/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class Stop {
    ActionListener listen;
    Timer timer;
    
    public Stop(Thread t){
        listen = new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent arg0){
                try{
                    t.wait();
                    
                    try{
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    t.notify();
                }
                catch(InterruptedException ex){
                    Logger.getLogger(Stop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        timer = new Timer(0, listen);
        timer.setRepeats(false);
        timer.start();
    }
}
