package core;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
* 
* Klasa inicjalizujaca robota, jego wlasciwosci oraz statki.
* Generowanie losowego rozmieszczenia statkow oraz losowych trafien robota w plansze gracza
*
*/
public class Robot extends Player {

	//rodzaje statkow
	private Ship shipLength5 = new Ship(5, "Rozmiar statku 5");
	private Ship shipLength4 = new Ship(4, "Rozmiar statku 4");
	private Ship shipLength3 = new Ship(3, "Rozmiar statku 3");
	private Ship shipLength2 = new Ship(2, "Rozmiar statku 2");
	private Ship shipLength1 = new Ship(1, "Rozmiar statku 1");

	public Ship getShipLength5() {
		return shipLength5;
	}

	public void setShipLength5(Ship shipLength5) {
		this.shipLength5 = shipLength5;
	}

	public Ship getShipLength4() {
		return shipLength4;
	}

	public void setShipLength4(Ship shipLength4) {
		this.shipLength4 = shipLength4;
	}

	public Ship getShipLength3() {
		return shipLength3;
	}

	public void setShipLength3(Ship shipLength3) {
		this.shipLength3 = shipLength3;
	}

	public Ship getShipLength2() {
		return shipLength2;
	}

	public void setShipLength2(Ship shipLength2) {
		this.shipLength2 = shipLength2;
	}

	public Ship getShipLength1() {
		return shipLength1;
	}

	public void setShipLength1(Ship shipLength1) {
		this.shipLength1 = shipLength1;
	}
	//tablica komorek dla planszy 
	ArrayList<Cell> cells = new ArrayList<Cell>();

	Player enemy;

	private Chart chart = new Chart(false);

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart chart) {
		this.chart = chart;
	}

	Cell currentTarget;
	Cell previousTarget;
	Ship currentChosenShip;

	TreeMap<String, Ship> ships = new TreeMap<String, Ship>();
	ArrayList<Cell> notHitedEnemyCells = new ArrayList<Cell>();

	Random generator = new Random();

	public Robot() {
		putShipsIntoTreeMap();
	}

	public void fillEnemyChart() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				notHitedEnemyCells.add(enemy.getChart().getCells()[i][j]);

			}
		}

	}
	// ustawienie statkow do planszy
	public void putShipsToChart() {
		for (Map.Entry<String, Ship> entry : ships.entrySet()) {
			putRobotShipOnGrids(entry.getValue());
		}
	}
	//dodanie statkow do mapy - nazwa statku + rozmiar
	private void putShipsIntoTreeMap() {

		ships.put(shipLength5.getName(), shipLength5);
		ships.put(shipLength4.getName(), shipLength4);
		ships.put(shipLength3.getName(), shipLength3);
		ships.put(shipLength2.getName(), shipLength2);
		ships.put(shipLength1.getName(), shipLength1);

	}

	/**
	 * Tutaj metody sa specyficzne dla robota czyli losowe ustawienie statkow
	 */
	private void putRobotShipOnGrids(Ship ship) {

		currentChosenShip = ship;
		//losowe wybranie orientacji statku
		int orientation_index = generator.nextInt(2);//wybor rozmieszczania statku - pion/poziom
		int x = generator.nextInt(10);
		int y = generator.nextInt(10);

		if (orientation_index == 0) {
			ship.setOrientation_horizontal(true);
		} else {
			ship.setOrientation_horizontal(false);
		}
		
		//sprawdzenie czy statek moze ma miejsce wolna dla ustawienia go
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (chart.getCells()[i][j].getLinkedShip() == null) {
					chart.getCells()[i][j].setChoosenShip(ship);
				}

			}
		}
		//warunek na nakladanie sie statkow bedzie, tak dlugo probowal az
		//znajdzie komorki gdzie moze ustawic statek
		while (chart.getCells()[x][y].isSpaceForCellOrCellIsNotUsed() == false) {
			x = generator.nextInt(10);
			y = generator.nextInt(10);
			orientation_index = generator.nextInt(2);

		}

		putShipToChart(chart.getCells()[x][y]);

	}

	private void hitInEnemyChart(Cell cell) {

		cell.getHit();

	}
	//metoda losowo oddajace trafienie na plansze gracza
	public void fireOnPlayerChart() {
		int x = generator.nextInt(10);
		int y = generator.nextInt(10);
		while (!notHitedEnemyCells.contains(this.enemy.getChart().getCells()[x][y])) {
			x = generator.nextInt(10);
			y = generator.nextInt(10);
		}
		currentTarget = enemy.getChart().getCells()[x][y];
		hitInEnemyChart(currentTarget);
		notHitedEnemyCells.remove(currentTarget);
	}
	//ustawienie statku albo w pionie albo w poziomie
	public void putShipToChart(Cell cell) {

		for (int i = 0; i < currentChosenShip.getSize(); i++) {
			cell.setShipToPut(currentChosenShip);
			if (currentChosenShip.isOrientationHorizontal() == true) {
				cell = cell.getNextCellInRow();
			} else {
				cell = cell.getNextCellInColumn();
			}
		}

	}
	// flaga czy wszystkie statki sa juz trafione
	public boolean allShipsAreSunk() {

		if (shipLength5.isSunk() && shipLength4.isSunk() && shipLength3.isSunk() && shipLength2.isSunk()
				&& shipLength1.isSunk()) {
			return true;
		} else {
			return false;
		}

	}

}
