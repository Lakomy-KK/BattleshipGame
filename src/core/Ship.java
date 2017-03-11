package core;

import java.awt.Color;

/**
 * Klasa odpowiedzalna za model statkow
 * @author Krzysiek
 *
 */
public class Ship {

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOrientationHorizontal() {
		return orientationHorizontal;
	}

	public void setOrientation_horizontal(boolean orientation_horizontal) {
		this.orientationHorizontal = orientation_horizontal;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}


	private int size = 0;
	private String name;
	private boolean orientationHorizontal = true;
	private boolean[] hits;
	private boolean isSunk = false;

	private boolean shipIsSet;

	public boolean isShipIsSet() {
		return shipIsSet;
	}

	public void setShipIsSet(boolean shipIsSet) {
		this.shipIsSet = shipIsSet;
	}

	Color color = new Color(0, 0, 0);

	private int hitIndex = 0;

	public Ship(int size, String name) {
		this.size = size;
		this.name = name;
		hits = new boolean[size];
		for (int i = 0; i < this.size; i++) {

			hits[i] = false;
		}
		setShipColors();
	}

	/*
	 * tutaj sa ustawienia kolorow statkow
	 */
	private void setShipColors() {
		if (size == 5) {
			this.color = new Color(176, 196, 222);

		} else if (size == 4) {
			this.color = new Color(143, 188, 143);
		} else if (size == 3) {
			this.color = new Color(255, 215, 0);
		} else if (size == 2) {
			this.color = new Color(188, 143, 143);
		} else if (size == 1) {
			this.color = new Color(160, 32, 240);
		}
	}
	//metoda obslugujaca obrocenie statku przy ustawianiu na planszy
	public void rotate() {
		orientationHorizontal = !orientationHorizontal;
	}

	/*
	 * sprawdzenie czy zatopiony na podstawie ilosc 1 w tablicy boolean []hits
	 * Jesli ilosc indeksow tablicy == rozmiar statku to statek zatopiony
	 */
	public boolean isSunk() {
		int index = 0;
		for (int i = 0; i < this.size; i++) {
			if (this.hits[i] == true) {
				index ++;
			}
		}
		if (index == this.size) {
			this.isSunk = true;
			return true;
		}
		return false;

	}
	//przekazanie informacji o ilosci trafien tablicy hits[]. 
	public void sendHitToShip() {
		this.hits[hitIndex] = true;
		hitIndex++;
	}
}
