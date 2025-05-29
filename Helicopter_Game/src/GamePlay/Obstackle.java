/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GamePlay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Obstackle {

	/** Grafika reprezentująca przeszkodę dla poziomu 1. */
	public static BufferedImage level1;
	/** Grafika reprezentująca przeszkodę dla poziomu 2. */
	public static BufferedImage level2;
	/** Grafika reprezentująca przeszkodę dla poziomu 3. */
	public static BufferedImage level3;
	/** Zmienna wskazująca pozycję przeszkody w poziomie. */ 
	private static int xPosition; 
	/** Zmienna wskazująca pozycję przeszkody w pionie. */ 
	private static int yPosition;  

	/** Konstruktor klasy, który inizjalizuje zmienne pozycji oraz wczytuje grafiki przeszkód dla poszczególnych poziomów. */
	public Obstackle(){

		xPosition = 700;
		yPosition = 330;

		File imageFile = new File("src/Resources/Level1.png");

		try{

			level1 = ImageIO.read(imageFile);

		}	
		catch(IOException e){

			System.err.println("Error reading level 1!");
			e.printStackTrace();

		}

		imageFile = new File("src/Resources/Level2.png");

		try{

			level2 = ImageIO.read(imageFile);

		}	
		catch(IOException e){

			System.err.println("Error reading level 2!");
			e.printStackTrace();

		}

		imageFile = new File("src/Resources/Level3.png");

		try{

			level3 = ImageIO.read(imageFile);

		}	
		catch(IOException e){

			System.err.println("Error reading level 3!");
			e.printStackTrace();

		}

	}

	/** Metoda zwracająca pozycję przeszkody w pionie. 
	 *  @return Zwraca aktualna pozycję przeszkody w pionie.
	 */
	public int getY(){

		return this.yPosition;

	}

	/** Metoda zwracająca pozycję przeszkody w poziomie.
	 *  @return Zwraca aktualną pozycje przeszkody w poziomie.
	 */
	public int getX(){

		return this.xPosition;

	}

	/** Metoda zmniejszająca pozycję helikoptera w poziomie.
	 *  @param x - Wartość o jaką zmniejszona ma zostać pozycja helikoptera w poziomie.
	 */
	public void setXDown(int x){

		this.xPosition -= x;
	}

	/** Metoda ustawiająca pozycję początkową dla każdej nowej przeszkody.
	 *  @param x - Pozycja przeszkody w poziomie.
	 *  @param y - Pozycja przeszkody w pionie (losowa).
	 */
	public void setStartingPosition(int x, int y){

		this.xPosition = x;
		this.yPosition = y;

	}

	// Metoda dodana podczas oddawania projektu
	public void setXUp(int x){

		this.xPosition += x;
		
	}
	
}