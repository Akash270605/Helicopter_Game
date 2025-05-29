/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Action.Flying;
import Action.KeyListening;
import Action.MouseListening;
import Action.Stop;
import GamePlay.Helicopter;
import GamePlay.Map;
import GamePlay.Obstackle;
import GamePlay.Rocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionPanel extends JPanel{
    
    private static final long serialVersionUID = 1L;
    private static final int level2Score = 6;
    private static final int level3Score = 12;
    private static final int bonusScore = 3;
    private static BufferedImage menuImage;
    private static ImageIcon buttonStartIcon;
    private static ImageIcon buttonExitIcon;
    private static JButton startButton;
    private static JButton exitButton;
    private static JLabel score;
    private static JLabel level;
    private static Flying flying;
    private static Helicopter helicopter;
    private static Obstackle obstackle;
    private static Map map;
    private static Thread threadFly;
    private static MouseListening mouseListen;
    private static Random randomPosition;
    private static Rocket rocket;
    private static int n;
    private static KeyListening KeyListen;
    
    public ActionPanel(){
        this.setLayout(null);
        
        startButton = new JButton();
        startButton.setSize(150, 100);
        startButton.setLocation(120, 200);
        buttonStartIcon = new ImageIcon("src/Resources/startIcon.png");
        startButton.setIcon(buttonStartIcon);
        this.add(startButton);
        
        exitButton= new JButton();
        exitButton.setSize(150, 100);
        exitButton.setLocation(120, 350);
        buttonExitIcon = new ImageIcon("src/Resources/exitIcon.png");
        exitButton.setIcon(buttonExitIcon);
        this.add(exitButton);
        
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(1);
            }
        });
        
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                remove(startButton);
                remove(exitButton);
                
                randomPosition = new Random();
                
               map = new Map();
               helicopter = new Helicopter();
               obstackle = new Obstackle();
               rocket = new Rocket();
               
               mouseListen = new MouseListening(helicopter);
               addMouseListener(mouseListen);
               
               KeyListen= new KeyListening();
               addKeyListener(KeyListen);
               requestFocus();
               
               flying = new Flying(helicopter, obstackle, rocket, map);
               threadFly = new Thread(flying);
               threadFly.start();
               
               score = new JLabel();
               score.setSize(300, 50);
               score.setFont(new Font("Serif", Font.BOLD, 45));
               score.setForeground(Color.yellow.brighter());
               add(score);
               score.setLocation(45, 532);
               score.setText("SCORE: " + Integer.toString(helicopter.getScore()));
               
               level = new JLabel();
               level.setSize(300, 50);
               level.setFont(new Font("Serif", Font.BOLD, 45));
               level.setForeground(Color.yellow.brighter());
               add(level);
               level.setLocation(575, 532);
               level.setText("LEVEL: 1");
               
               map.setLevel(1);
               repaint();
               
               Stop stop = new Stop(threadFly);
            }
        });
        
        File imageFile = new File("src/Resources/Background.png");
        
        try{
            menuImage = ImageIO.read(imageFile);
        }
        catch(IOException e){
            System.err.println("Error reading Menu");
            e.printStackTrace();
        }
        
        Dimension dimension = new Dimension(menuImage.getWidth(), menuImage.getHeight());
        setPreferredSize(dimension);
    }
    
    private void stopGame(){
        try {
            threadFly.wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(ActionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        threadFly.stop();
        
        removeMouseListener(mouseListen);
        remove(score);
        remove(level);
        
        add(startButton);
        add(exitButton);
    }
    
    @SuppressWarnings("deprecation")
    
   public void paintComponent(Graphics g) {

		switch(map.level){ // Tutaj zamiast map.level powinna byc metoda map.getLevel(), ale gra się wtedy wysypywała. Nie chciało mi się już szukać przyczyny.

		// Menu
		case 0:

			// Rysowanie menu
			g.drawImage(menuImage, 0, 0, this);
			break;

		// Poziom 1	
		case 1:

			// W przypadku wykrycia jakiejkolwiek kolizji (z górą, dołem, klockiem)
			if(helicopter.getCrashState() == false && ((helicopter.getY() < 87 || helicopter.getY() > 465) || 
					((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 33) && (helicopter.getY() > obstackle.getY() && helicopter.getY() < obstackle.getY() + 162)) ||
					((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 33) && (helicopter.getY() + 44 > obstackle.getY() && helicopter.getY() + 44 < obstackle.getY() + 162)) ||
					((helicopter.getX() > obstackle.getX() && helicopter.getX() < obstackle.getX() + 33) && (helicopter.getY() + 22 > obstackle.getY() && helicopter.getY() + 22 < obstackle.getY() + 162)))) {


				helicopter.setState("crash");

				// Jeżeli kolizja była z dołem (bez spadania) nie będzie animacji spadania po rozbiciu
				if(helicopter.getY() > 465) {

					// Zakończenie gry i wrócenie do menu
					stopGame();
					g.drawImage(menuImage, 0, 0, this);
					break;

				}

			}
			// W przypadku gdy kolizja została już wykryta i nie była to kolizja z dołem (animacja spadania po rozbiciu) lub helikopter nie ma stanu rozbicia
			else{

				// W przypadku gdy helikopter jest w stanie rozbicia 
				if(helicopter.getCrashState() == true){

					// Utrzymanie odpowiedniego otoczenia (mapa, przeszkody) podczas gdy spada rozbity helikopter
					g.drawImage(map.map1, 0, 0, null);
					g.drawImage(obstackle.level1, obstackle.getX(), obstackle.getY(), null);
					g.drawImage(helicopter.helicopterCrashed, helicopter.getX(), helicopter.getY(), null);
					g.drawImage(map.gameOver, 150, 0, null);

					// Jeśli helikopter osiądzie na dolnej granicy
					if(helicopter.getY() > 465) {

						// Zakończenie gry i wrócenie do menu
						stopGame();
						g.drawImage(menuImage, 0, 0, this);
						break;

					}

				}
				// W przypadku gdy helikopter nie jest w stanie rozbicia
				else{

					// Jeśli bonus jest aktywny i helikopter zgarnie bonus
					if(map.getBonusState() == true && (((helicopter.getX() + 95 >= rocket.getX() && helicopter.getX() + 95 <= rocket.getX() + 40) &&
							(helicopter.getY() >= rocket.getY() && helicopter.getY() <= rocket.getY() + 40)) ||
							((helicopter.getX() + 95 >= rocket.getX() && helicopter.getX() + 95 <= rocket.getX() + 40) &&
									helicopter.getY() + 44 >= rocket.getY() && helicopter.getY() + 44 <= rocket.getY() + 40))){

						// Dezaktywacja bonusa, wygenerowanie nowej przyszłej pozycji, dodanie punktów bonusowych i zmiana etykiety
						map.setBonus("OFF");
						rocket.setX(randomPosition.nextInt(500) + 1000);
						rocket.setY(randomPosition.nextInt(300) + 120);
						helicopter.addScore(bonusScore);
						score.setText("SCORE: " + Integer.toString(helicopter.getScore()));

					}

					// Jeśli helikopter się wznosi
					if(helicopter.getFlyState() == true){

						g.drawImage(map.map1, 0, 0, null);
						g.drawImage(helicopter.helicopterAscending, helicopter.getX(), helicopter.getY(), null);

						// Jeśli bonus jest aktywny
						if(map.getBonusState() == true) {

							// Jeśli bonus został pominięty przez helikopter
							if(rocket.getX() < 0) {

								// Dezaktywacja bonusa, wygenerowanie nowej przyszłej pozycji
								map.setBonus("OFF");
								rocket.setX(randomPosition.nextInt(500) + 1000);
								rocket.setY(randomPosition.nextInt(300) + 120);

							}
							// Jeśli bonus jeszcze nie został pominięty przez helikopter
							else{

								g.drawImage(rocket.bonus, rocket.getX(), rocket.getY(), null); 

							}

						}

					}
					// Jeśli helikopter opada
					else{

						g.drawImage(map.map1, 0, 0, null);
						g.drawImage(helicopter.helicopterDescending, helicopter.getX(), helicopter.getY(), null);

						// Jeśli bonus jest aktywny
						if(map.getBonusState() == true) {

							// Jeśli bonus został pominięty przez helikopter
							if(rocket.getX() < 0) {

								// Dezaktywacja bonusa, wygenerowanie nowej przyszłej pozycji
								map.setBonus("OFF");
								rocket.setX(randomPosition.nextInt(500) + 1000);
								rocket.setY(randomPosition.nextInt(300) + 120);

							}
							// Jeśli bonus jeszcze nie został pominięty przez helikopter
							else{

								g.drawImage(rocket.bonus, rocket.getX(), rocket.getY(), null); 

							}

						}

					}

					// Jeśli zdobyte punkty są większe lub równe limitowi na poziom 2
					if(helicopter.getScore() >= level2Score) {

						// Ustawienie poziomu bonusa na 2, zmiana etykiety i podniesienie flagi pierwszej zmiany mapy
						map.setLevel(2);
						level.setText("LEVEL: " + Integer.toString(map.getLevel()));
						map.setMapChange("ON");

					}

					// Jeśli przeszkoda zniknęła z pola widzenia i jest to 1 poziom
					if(obstackle.getX() < 0 && map.getLevel() == 1) { 

						// Wygenerowanie nowej pozycji przeszkody, dodanie punkta, zmiana etykiety i próba wygenerowania nowego bonusa
						obstackle.setStartingPosition(800, randomPosition.nextInt(220) + 115);
						helicopter.addScore(1); 
						n = randomPosition.nextInt(9) + 1;
						if(helicopter.getScore() % n == 0) map.setBonus("ON");
						score.setText("SCORE: " + Integer.toString(helicopter.getScore()));

					}

					g.drawImage(obstackle.level1, obstackle.getX(), obstackle.getY(), null);

				} 

			}

			break;

		// Poziom 2	
		case 2:

			// W przypadku gdy flaga zmiany mapy jest podniesiona
			if(map.getMapChange() == true){

				// W przypadku wykrycia jakiejkolwiek kolizji (z górą, dołem, klockiem) i gdy wcześniej nie było kolizji
				if(helicopter.getCrashState() == false && ((helicopter.getY() < 87 || helicopter.getY() > 465) || 
						((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 33) && (helicopter.getY() > obstackle.getY() && helicopter.getY() < obstackle.getY() + 162)) ||
						((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 33) && (helicopter.getY() + 44 > obstackle.getY() && helicopter.getY() + 44 < obstackle.getY() + 162)) ||
						((helicopter.getX() > obstackle.getX() && helicopter.getX() < obstackle.getX() + 33) && (helicopter.getY() + 22 > obstackle.getY() && helicopter.getY() + 22 < obstackle.getY() + 162)))) {

					helicopter.setState("crash");

					// Jeżeli kolizja była z dołem (bez spadania) nie będzie animacji spadania po rozbiciu
					if(helicopter.getY() > 465) {

						// Zakończenie gry i wrócenie do menu
						stopGame();
						g.drawImage(menuImage, 0, 0, this);
						break;

					}

				}

			}

			// Jeśli flaga zmiany mapy jest opuszczona, nie było wcześniej kolizji i została ona wykryta
			if(map.getMapChange() == false && (helicopter.getCrashState() == false && ((helicopter.getY() < 87 || helicopter.getY() > 465) || 
					((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 35) && (helicopter.getY() > obstackle.getY() && helicopter.getY() < obstackle.getY() + 206)) ||
					((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 35) && (helicopter.getY() + 44 > obstackle.getY() && helicopter.getY() + 44 < obstackle.getY() + 206)) ||
					((helicopter.getX() > obstackle.getX() && helicopter.getX() < obstackle.getX() + 35) && (helicopter.getY() + 22 > obstackle.getY() && helicopter.getY() + 22 < obstackle.getY() + 206))))) {

				helicopter.setState("crash");

				// Jeżeli kolizja była z dołem (bez spadania) nie będzie animacji spadania po rozbiciu
				if(helicopter.getY() > 465) {

					// Zakończenie gry i wrócenie do menu
					stopGame();
					g.drawImage(menuImage, 0, 0, this);
					break;

				}

			}
			// W przypadku gdy helikopter jest już w stanie rozbicia, nie wykryto kolizji lub flaga zmiany mapy jest podniesiona
			else{

				// Jeśli helikopter jest rozbity
				if(helicopter.getCrashState() == true){

					g.drawImage(map.map2, 0, 0, null);

					// Jeśli flaga zmiany mapy jest podniesiona
					if(map.getMapChange() == true){

						g.drawImage(obstackle.level1, obstackle.getX(), obstackle.getY(), null);

					}
					// Jeśli flaga zmiany mapy nie jest podniesiona
					else{

						g.drawImage(obstackle.level2, obstackle.getX(), obstackle.getY(), null);

					}

					g.drawImage(helicopter.helicopterCrashed, helicopter.getX(), helicopter.getY(), null);
					g.drawImage(map.gameOver, 150, 0, null);

					// Jeśli helikopter osiądzie na dolnej granicy
					if(helicopter.getY() > 465) {

						// Zakończenie gry i wrócenie do menu
						stopGame();
						g.drawImage(menuImage, 0, 0, this);
						break;

					}

				}
				// Jeśli helikopter nie jest rozbity
				else{

					// Jeśli bonus jest aktywny i helikopter zgarnie bonus
					if((map.getBonusState() == true && (((helicopter.getX() + 95 >= rocket.getX() && helicopter.getX() + 95 <= rocket.getX() + 40) &&
							(helicopter.getY() >= rocket.getY() && helicopter.getY() <= rocket.getY() + 40)) ||
							((helicopter.getX() + 95 >= rocket.getX() && helicopter.getX() + 95 <= rocket.getX() + 40) &&
									helicopter.getY() + 44 >= rocket.getY() && helicopter.getY() + 44 <= rocket.getY() + 40)))){

						// Dezaktywacja bonusa, wygenerowanie nowej przyszłej pozycji, dodanie punktów bonusowych i zmiana etykiety
						map.setBonus("OFF");
						rocket.setX(randomPosition.nextInt(500) + 1000);
						rocket.setY(randomPosition.nextInt(300) + 120);
						helicopter.addScore(bonusScore);
						score.setText("SCORE: " + Integer.toString(helicopter.getScore()));

					}

					// Jeśli helikopter się wznosi
					if(helicopter.getFlyState() == true){

						g.drawImage(map.map2, 0, 0, null);
						g.drawImage(helicopter.helicopterAscending, helicopter.getX(), helicopter.getY(), null);

						// Jeśli bonus jest aktywny
						if(map.getBonusState() == true) {

							// Jeśli bonus został pominięty przez helikopter
							if(rocket.getX() < 0) {

								// Dezaktywacja bonusa, wygenerowanie nowej przyszłej pozycji
								map.setBonus("OFF");
								rocket.setX(randomPosition.nextInt(500) + 1000);
								rocket.setY(randomPosition.nextInt(300) + 120);

							}
							// Jeśli bonus jeszcze nie został pominięty przez helikopter
							else{

								g.drawImage(rocket.bonus, rocket.getX(), rocket.getY(), null); 

							}

						}

					}
					// Jeśli helikopter opada
					else{

						g.drawImage(map.map2, 0, 0, null);
						g.drawImage(helicopter.helicopterDescending, helicopter.getX(), helicopter.getY(), null);

						// Jeśli bonus jest aktywny
						if(map.getBonusState() == true) {

							// Jeśli bonus został pominięty przez helikopter
							if(rocket.getX() < 0) {

								// Dezaktywacja bonusa, wygenerowanie nowej przyszłej pozycji
								map.setBonus("OFF");
								rocket.setX(randomPosition.nextInt(500) + 1000);
								rocket.setY(randomPosition.nextInt(300) + 120);

							}
							// Jeśli bonus jeszcze nie został pominięty przez helikopter
							else{

								g.drawImage(rocket.bonus, rocket.getX(), rocket.getY(), null); 

							}

						}

					}

					// Jeśli zdobyte punkty są większe lub równe limitowi na poziom 3
					if(helicopter.getScore() >= level3Score) {

						// Ustawienie poziomu bonusa na 3, zmiana etykiety i podniesienie flagi pierwszej zmiany mapy
						map.setLevel(3);
						level.setText("LEVEL: " + Integer.toString(map.getLevel()));
						map.setMapChange("ON");

					}

					// Jeśli przeszkoda zniknęła z pola widzenia i jest to 2 poziom
					if(obstackle.getX() < 0 && map.getLevel() == 2) { 

						// Opuszczenie flagi zmiany mapy, wygenerowanie nowej pozycji przeszkody, dodanie punkta, zmiana etykiety, próba wygenerowania nowego bonusa
						map.setMapChange("OFF");
						obstackle.setStartingPosition(800, randomPosition.nextInt(186) + 115);
						helicopter.addScore(1); 
						n = randomPosition.nextInt(9) + 1;
						if(helicopter.getScore() % n == 0) map.setBonus("ON");
						score.setText("SCORE: " + Integer.toString(helicopter.getScore()));

					}

					// Jeśli flaga zmiany mapy jest podniesiona 
					if(map.getMapChange() == true){

						g.drawImage(obstackle.level1, obstackle.getX(), obstackle.getY(), null);

					}
					// Jeśli flaga zmiany mapy nie jest podniesiona 
					else{

						g.drawImage(obstackle.level2, obstackle.getX(), obstackle.getY(), null);

					}

				}  

			}

			break;

		// Poziom 3	
		case 3:

			// W przypadku gdy flaga zmiany mapy jest podniesiona
			if(map.getMapChange() == true){

				// W przypadku wykrycia jakiejkolwiek kolizji (z górą, dołem, klockiem) i gdy wcześniej nie było kolizji
				if(helicopter.getCrashState() == false && ((helicopter.getY() < 87 || helicopter.getY() > 465) || 
						((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 35) && (helicopter.getY() > obstackle.getY() && helicopter.getY() < obstackle.getY() + 206)) ||
						((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 35) && (helicopter.getY() + 44 > obstackle.getY() && helicopter.getY() + 44 < obstackle.getY() + 206)) ||
						((helicopter.getX() > obstackle.getX() && helicopter.getX() < obstackle.getX() + 35) && (helicopter.getY() + 22 > obstackle.getY() && helicopter.getY() + 22 < obstackle.getY() + 206)))) {

					helicopter.setState("crash");

					// Jeżeli kolizja była z dołem (bez spadania) nie będzie animacji spadania po rozbiciu
					if(helicopter.getY() > 465) {

						// Zakończenie gry i wrócenie do menu
						stopGame();
						g.drawImage(menuImage, 0, 0, this);
						break;

					}

				}

			}

			// Jeśli flaga zmiany mapy jest opuszczona, nie było wcześniej kolizji i została ona wykryta
			if(map.getMapChange() == false && (helicopter.getCrashState() == false && ((helicopter.getY() < 87 || helicopter.getY() >= 465) || 
					((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 37) && (helicopter.getY() > obstackle.getY() && helicopter.getY() < obstackle.getY() + 245)) ||
					((helicopter.getX() + 95 > obstackle.getX() && helicopter.getX() + 95 < obstackle.getX() + 37) && (helicopter.getY() + 44 > obstackle.getY() && helicopter.getY() + 44 < obstackle.getY() + 245)) ||
					((helicopter.getX() > obstackle.getX() && helicopter.getX() < obstackle.getX() + 37) && (helicopter.getY() + 22 > obstackle.getY() && helicopter.getY() + 22 < obstackle.getY() + 245))))) {

				helicopter.setState("crash");

				// Jeżeli kolizja była z dołem (bez spadania) nie będzie animacji spadania po rozbiciu
				if(helicopter.getY() > 465) {

					// Zakończenie gry i wrócenie do menu
					stopGame();
					g.drawImage(menuImage, 0, 0, this);
					break;

				}

			}
			// W przypadku gdy helikopter jest już w stanie rozbicia, nie wykryto kolizji lub flaga zmiany mapy jest podniesiona
			else{

				// Jeśli helikopter jest rozbity
				if(helicopter.getCrashState() == true){

					g.drawImage(map.map3, 0, 0, null);

					// Jeśli flaga zmiany mapy jest podniesiona
					if(map.getMapChange() == true){

						g.drawImage(obstackle.level2, obstackle.getX(), obstackle.getY(), null);

					}
					// Jeśli flaga zmiany mapy nie jest podniesiona
					else{

						g.drawImage(obstackle.level3, obstackle.getX(), obstackle.getY(), null);

					}

					g.drawImage(helicopter.helicopterCrashed, helicopter.getX(), helicopter.getY(), null);
					g.drawImage(map.gameOver, 150, 0, null);

					// Jeśli helikopter osiądzie na dolnej granicy
					if(helicopter.getY() > 465) {

						// Zakończenie gry i wrócenie do menu
						stopGame();
						g.drawImage(menuImage, 0, 0, this);
						break;

					}

				}
				// Jeśli helikopter nie jest rozbity
				else{

					// Jeśli bonus jest aktywny i helikopter zgarnie bonus
					if(map.getBonusState() == true && (((helicopter.getX() + 95 >= rocket.getX() && helicopter.getX() + 95 <= rocket.getX() + 40) &&
							(helicopter.getY() >= rocket.getY() && helicopter.getY() <= rocket.getY() + 40)) ||
							((helicopter.getX() + 95 >= rocket.getX() && helicopter.getX() + 95 <= rocket.getX() + 40) &&
									helicopter.getY() + 44 >= rocket.getY() && helicopter.getY() + 44 <= rocket.getY() + 40))){

						// Dezaktywacja bonusa, wygenerowanie nowej przyszłej pozycji, dodanie punktów bonusowych i zmiana etykiety
						map.setBonus("OFF");
						rocket.setX(randomPosition.nextInt(500) + 1000);
						rocket.setY(randomPosition.nextInt(300) + 120);
						helicopter.addScore(bonusScore);
						score.setText("SCORE: " + Integer.toString(helicopter.getScore()));

					}

					// Jeśli helikopter się wznosi
					if(helicopter.getFlyState() == true){

						g.drawImage(map.map3, 0, 0, null);
						g.drawImage(helicopter.helicopterAscending, helicopter.getX(), helicopter.getY(), null);

						// Jeśli bonus jest aktywny
						if(map.getBonusState() == true){

							// Jeśli bonus został pominięty przez helikopter
							if(rocket.getX() < 0){

								// Dezaktywacja bonusa, wygenerowanie nowej przyszłej pozycji
								map.setBonus("OFF");
								rocket.setX(randomPosition.nextInt(500) + 1000);
								rocket.setY(randomPosition.nextInt(300) + 120);

							}
							// Jeśli bonus jeszcze nie został pominięty przez helikopter
							else{

								g.drawImage(rocket.bonus, rocket.getX(), rocket.getY(), null); 

							}

						}

					}
					// Jeśli helikopter opada
					else{

						g.drawImage(map.map3, 0, 0, null);
						g.drawImage(helicopter.helicopterDescending, helicopter.getX(), helicopter.getY(), null);

						// Jeśli bonus jest aktywny
						if(map.getBonusState() == true){

							// Jeśli bonus został pominięty przez helikopter
							if(rocket.getX() < 0){

								// Dezaktywacja bonusa, wygenerowanie nowej przyszłej pozycji
								map.setBonus("OFF");
								rocket.setX(randomPosition.nextInt(500) + 1000);
								rocket.setY(randomPosition.nextInt(300) + 120);

							}
							// Jeśli bonus jeszcze nie został pominięty przez helikopter
							else{

								g.drawImage(rocket.bonus, rocket.getX(), rocket.getY(), null); 

							}

						}

					}

					// Jeśli przeszkoda zniknęła z pola widzenia i jest to 3 poziom
					if(obstackle.getX() < 0 && map.getLevel() == 3){ 

						// Opuszczenie flagi zmiany mapy, wygenerowanie nowej pozycji przeszkody, dodanie punkta, zmiana etykiety, próba wygenerowania nowego bonusa
						map.setMapChange("OFF");
						obstackle.setStartingPosition(800, randomPosition.nextInt(145) + 115);
						helicopter.addScore(1); 
						n = randomPosition.nextInt(1) + 9;
						if(helicopter.getScore() % n == 0) map.setBonus("ON");
						score.setText("SCORE: " + Integer.toString(helicopter.getScore()));

					}

					// Jeśli flaga zmiany mapy jest podniesiona 
					if(map.getMapChange() == true){

						g.drawImage(obstackle.level2, obstackle.getX(), obstackle.getY(), null);

					}
					// Jeśli flaga zmiany mapy nie jest podniesiona 
					else{

						g.drawImage(obstackle.level3, obstackle.getX(), obstackle.getY(), null);

					}

				}  

			}

			break;

		}

	}

}