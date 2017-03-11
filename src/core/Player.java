package core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

/**
 * 
 * Klasa inicjalizujaca gracza, jego wlasciwosci oraz statki.
 * Nasluchiwanie akcji gracza, logika ruchow gracza
 *
 */

public class Player {

	private JComboBox<String> comboShips;

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

	private boolean startGame = false;
	boolean endGame = false;

	// rodzaje statkow
	private Ship shipLength5 = new Ship(5, "Rozmiar statku 5");
	private Ship shipLength4 = new Ship(4, "Rozmiar statku 4");
	private Ship shipLength3 = new Ship(3, "Rozmiar statku 3");
	private Ship shipLength2 = new Ship(2, "Rozmiar statku 2");
	private Ship shipLength1 = new Ship(1, "Rozmiar statku 1");

	// odwzorowanie nazw na statki i na odpowiednie buttony
	private TreeMap<String, Ship> ships = new TreeMap<String, Ship>();

	// plansza robota
	private ArrayList<Cell> robotChart = new ArrayList<Cell>();

	private Ship currentChosenShip = new Ship(0, "");

	JButton start = new JButton("Start Gry !");
	JCheckBox rotate = new JCheckBox("Po³o¿enie pionowe statku");
	JButton reset = new JButton("Reset statków !");

	// inicjalizacji planszy gracza
	private Chart chart = new Chart(true);

	// plansza na ktorej ustawiamy statki
	Cell currentCell;

	Robot enemy;

	// listener na to czy statek moze byc wlozony w miejsce ktore wybrano
	MouseAdapter listenerForPutShips = new MouseAdapter() {

		public void mouseClicked(MouseEvent e) {
			if (startGame == false && endGame == false) {
				JTable x = (JTable) e.getSource();

				/** sprawdzenie czy mozna polozyc statek w danym miejscu jesli
				 *
				 * wszystkie sa wolne to mozemy polozyc statek
				 * 
				 * po polozeniu statek jest usuwany z listy do wyboru
				 */
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (chart.getCells()[i][j].getChart().equals(x)) {
							currentCell = chart.getCells()[i][j];
							if (currentCell.getChoosenShip() != null || currentChosenShip.isShipIsSet() == false) {
								if (currentCell.isSpaceForCellOrCellIsNotUsed()) {

									putShipOnChart();
									JOptionPane.showMessageDialog(null,
											currentChosenShip.getName() + "\nStatek zosta³ ustawiony ",
											"Ustawianie statku", JOptionPane.INFORMATION_MESSAGE);
									currentChosenShip.setShipIsSet(true);
									chart.initialTheCurrentShipForEachCell();
									comboShips.removeItem(currentChosenShip.getName());
									comboShips.revalidate();

								}
							} else {
								JOptionPane.showMessageDialog(null, "Nie wybra³eœ statku", "Ustawianie statku",
										JOptionPane.INFORMATION_MESSAGE);
							}

						}
					}

				}
			}
		};

		// rozne kolory aby bylo widac gdzie statek zostal ustawiony
		public void mouseEntered(MouseEvent e) {

			((JTable) e.getSource()).setBorder(new LineBorder(new Color(255, 255, 255)));

		}

		public void mouseExited(MouseEvent e) {

			((JTable) e.getSource()).setBorder(new LineBorder(new Color(0, 0, 0)));

		}

	};

	ActionListener listenerForRotateChecker = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			for (Map.Entry<String, Ship> entry : ships.entrySet()) {
				entry.getValue().rotate();

			}
		}
	};

	/**
	 * resetowanie powoduje wpisanie obecnie wybranego statku na null i
	 * wczytanie nowej listy statkow do listy do wyboru
	 */
	ActionListener listenerForResetButton = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			// czyscimy wszystkie ustawienia statkow
			for (Map.Entry<String, Ship> entry : ships.entrySet()) {
				entry.getValue().setShipIsSet(false);
			}
			chart.resetShipPlacementOnEachSlot();
			comboShips.removeAllItems();
			for (Map.Entry<String, Ship> entry : getShips().entrySet()) {
				comboShips.addItem(entry.getValue().getName());
			}
			comboShips.revalidate();
			// currentChosenShip = null;

		}

	};
	// akcja na przycisk startu gry, walidacja czy wszystkie statki zosta³y ustanowione(metoda "allShipsAreSet()"
	ActionListener listenerForStartButton = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			if (allShipsAreSet()) {
				JOptionPane.showMessageDialog(null, "Zaczynamy !\nOddaj pierwszy strza³ !", "START",
						JOptionPane.INFORMATION_MESSAGE);
				startGame = true;
			} else {
				JOptionPane.showMessageDialog(null, "Ustaw statki na planszy !", "START", JOptionPane.ERROR_MESSAGE);
			}

		}

	};
	// Obsluga trafien gracza 
	MouseAdapter listenerForEnemyChart = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {

			if (startGame && endGame == false) {
				for (Cell cell : robotChart) {
					if (cell.getChart().equals(e.getSource()) && cell.isHit() == false) { //pobranie trafienia, sprawdzenie czy komorka zawiera statek

						cell.getHit();

						if (cell.getLinkedShip() != null) {
							if (cell.getLinkedShip().isSunk()) {
								JOptionPane.showMessageDialog(null, "Brawo , zatopi³eœ !", "",
										JOptionPane.INFORMATION_MESSAGE);
							} else {

								JOptionPane.showMessageDialog(null, "Brawo , trafi³eœ !", "",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
						if (enemy.allShipsAreSunk()) {
							endGame = true;
							JOptionPane.showMessageDialog(null, "Gratulacje, Wygra³eœ !", "Wygrana",
									JOptionPane.INFORMATION_MESSAGE);
						}
						// jesli nie jest juz skonczona gra to czas na robota
						else {
							enemy.fireOnPlayerChart();
							if (allShipsAreSunk()) {
								endGame = true;
								JOptionPane.showMessageDialog(null, "Niestety wygra³ robot....", "Przegrana",
										JOptionPane.INFORMATION_MESSAGE);

							}
						}

						break;
					}
				}

			} else if (!endGame) {
				JOptionPane.showMessageDialog(null, "B³¹d", "Gra nie rozpoczêta !", JOptionPane.ERROR_MESSAGE);
			} 
		}
		// ustawienie widocznosci poruszania myszka po planszy
		public void mouseEntered(MouseEvent e) {

			((JTable) e.getSource()).setBorder(new LineBorder(new Color(255, 255, 255)));

		}

		public void mouseExited(MouseEvent e) {

			((JTable) e.getSource()).setBorder(new LineBorder(new Color(0, 0, 0)));

		}

	};

	public Player() {

		putShipsIntoTreeMap();
		setPlayerControlOnPanelButtons();
		setPlayerControlOnChart();

	}
	//wybor statku do ustawienia, jesli juz ustawiony to usuniecie go z listy dostepnych statkow do ustawienia
	public void setCombo(JComboBox<String> comboShips) {
		this.comboShips = comboShips;
		comboShips.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String x = (String) comboShips.getSelectedItem();
				for (Map.Entry<String, Ship> entry : getShips().entrySet()) {
					if (entry.getValue().getName().equals(x)) {
						if (entry.getValue().isShipIsSet() == false) {
							currentChosenShip = entry.getValue();
							chart.setCurrentShipForEachCell(entry.getValue());
						}
					}

				}
			}
		});
	}
	//dodanie listenera dla dla planszy robota
	public void setPlayerControlOnEnemyChart() {

		for (Cell grid : robotChart) {
			grid.getChart().addMouseListener(listenerForEnemyChart);

		}

	}
	//ustawienie siatki przeciwnika
	public void fillEnemyChart() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				robotChart.add(enemy.getChart().getCells()[i][j]);
			}
		}

	}

	/**
	 * dodanie listenerow do buttonow
	 */
	private void setPlayerControlOnPanelButtons() {
		rotate.addActionListener(listenerForRotateChecker);
		reset.addActionListener(listenerForResetButton);
		start.addActionListener(listenerForStartButton);
	}

	public void disableButton(JButton button) {

		button.setEnabled(false);

	}

	/**
	 * inicjalizacja mapy: nazwa statku na obiekt statek(Ship)
	 */
	private void putShipsIntoTreeMap() {

		ships.put(shipLength5.getName(), shipLength5);
		ships.put(shipLength4.getName(), shipLength4);
		ships.put(shipLength3.getName(), shipLength3);
		ships.put(shipLength2.getName(), shipLength2);
		ships.put(shipLength1.getName(), shipLength1);

	}

	/*
	 * dodanie tylko listener do kazdej komorki w jtable
	 */
	public void setPlayerControlOnChart() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				this.chart.getCells()[i][j].getChart().addMouseListener(listenerForPutShips);
			}
		}

	}
	// logika ustawienia statkow na planszy przez gracza, odznaczenie kolorem wyroznienia na tle koloru komorek planszy
	public void putShipOnChart() {

		for (int i = 0; i < currentChosenShip.getSize(); i++) {

			currentCell.setShipToPut(currentChosenShip);
			currentCell.getChart().setBackground(currentChosenShip.color);
			if (currentChosenShip.isOrientationHorizontal() == true) {
				currentCell = currentCell.getNextCellInRow();
			} else {
				currentCell = currentCell.getNextCellInColumn();
			}
		}

	}
	//flaga czy wszystkie statki zostaly umieszczone na planszy gracza, mozliwosc startu gry
	public boolean allShipsAreSet() {

		for (Map.Entry<String, Ship> entry : ships.entrySet()) {

			if (entry.getValue().isShipIsSet() == false) {
				return false;
			}

		}
		disableButton(start);
		disableButton(reset);
		return true;

	}
	//metoda warunkuj¹ca zakonczenie gry poprzez trafienie wszystkich statkow
	public boolean allShipsAreSunk() {

		if (shipLength5.isSunk() && shipLength4.isSunk() && shipLength3.isSunk() && shipLength2.isSunk()
				&& shipLength1.isSunk()) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isStartGame() {
		return startGame;
	}

	public void setStartGame(boolean startGame) {
		this.startGame = startGame;
	}

	public boolean isEndGame() {
		return endGame;
	}

	public void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}

	public TreeMap<String, Ship> getShips() {
		return ships;
	}

	public void setShips(TreeMap<String, Ship> ships) {
		this.ships = ships;
	}

	public ArrayList<Cell> getRobotGrids() {
		return robotChart;
	}

	public void setRobotGrids(ArrayList<Cell> robotGrids) {
		this.robotChart = robotGrids;
	}

	public JButton getStart() {
		return start;
	}

	public void setStart(JButton start) {
		this.start = start;
	}

	public JButton getReset() {
		return reset;
	}

	public void setReset(JButton reset) {
		this.reset = reset;
	}

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart grids) {
		this.chart = grids;
	}

	public Robot getRobot() {
		return enemy;
	}

	public void setRobot(Robot robot) {
		this.enemy = robot;
	}

}
