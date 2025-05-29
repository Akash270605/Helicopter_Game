/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Graphics;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Window extends JFrame{
    public static ActionPanel actionPanel;
    private static final long serialVersionUID = 1L;
    
    public Window(String title){
        
        super(title);
        setSize(800, 600);
        setLocation(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/Resources/Icon.png"));
        setLayout(new BorderLayout());
        actionPanel = new ActionPanel();
        add(actionPanel);
        pack();
        setVisible(true);
    }
}
