package core;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
/**
 * Utworzenie pojedynczej komÛrki w planszy graczy
 * @author Krzysiek
 *
 */
public class Cell {

	private JTable chart;
	public JTable getChart() {
		return chart;
	}

	public void setChart(JTable chart) {
		this.chart = chart;
	}

	/*
	 * boolean isHorizontal albo kolumne albo wiersz
	 *
	 * i ma tez ilosc pamietanych uderzen
	 */
	private Cell previousCellInRow;
	private Cell previousCellInColumn;
	private Cell nextCellInRow;

	public Cell getPreviousCellInRow() {
		return previousCellInRow;
	}

	public void setPreviousCellInRow(Cell previousCellInRow) {
		this.previousCellInRow = previousCellInRow;
	}

	public Cell getPreviousCellInColumn() {
		return previousCellInColumn;
	}

	public void setPreviousCellInColumn(Cell previousCellInColumn) {
		this.previousCellInColumn = previousCellInColumn;
	}

	public Cell getNextCellInRow() {
		return nextCellInRow;
	}

	public void setNextCellInRow(Cell nextCellInRow) {
		this.nextCellInRow = nextCellInRow;
	}

	public Cell getNextCellInColumn() {
		return nextCellInColumn;
	}

	public void setNextCellInColumn(Cell nextCellInColumn) {
		this.nextCellInColumn = nextCellInColumn;
	}

	private Cell nextCellInColumn;

	private Cell checkCell;
	private Ship choosenShip;
	private Ship linkedShip;

	private boolean hit = false;

	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	public boolean isHit(){
		return hit;
	}

	public Cell(JTable table) {

		this.chart = table;

		checkCell = this;

	}

	/**
	 * 
	 *czy komorka jest wolna
	 * 
	 */
	public boolean isSpaceForCellOrCellIsNotUsed() {
		// sprawdz wpierw warunki na to czy jest wystarczajaco miejsca na
		// horyzontalne po≈Ço≈ºenie
		if(choosenShip == null){
			 JOptionPane.showMessageDialog(null, "B≥πd", "Najpierw wybierz statek",
			 JOptionPane.ERROR_MESSAGE);
		}
		if (choosenShip.isOrientationHorizontal() == true) {
			//petla sprawdz czy w linii jest wystarczajaco duzo komorek
			for (int i = 0; i < choosenShip.getSize(); i++) {
				if (checkCell == null) {
					return false;
				} else {

					checkCell = checkCell.nextCellInRow;
				}
			}

			checkCell = this;
			//czy komorka jest juz uzupelniona
			for (int i = 0; i < choosenShip.getSize(); i++) {
				if (checkCell.getLinkedShip() != null) {

					return false;
				} else {
					checkCell = checkCell.nextCellInRow;
				}
			}
			checkCell = this;
		} // ta sama procedura dla komÛrek w pionie
		else {

			for (int i = 0; i < choosenShip.getSize(); i++) {
				if (checkCell == null) {

					return false;
				} else {
					checkCell = checkCell.nextCellInColumn;
				}
			}

			checkCell = this;
			for (int i = 0; i < choosenShip.getSize(); i++) {
				if (checkCell.linkedShip != null) {

					return false;
				} else {
					checkCell = checkCell.nextCellInColumn;
				}
			}
			checkCell = this;
		}

		return true;
	}
	// zapamiÍtanie trafienia, oznaczenie kolorem na mapie
	public void getHit() {

		if (this.linkedShip != null) {
			this.linkedShip.sendHitToShip();
			this.chart.setBackground(Color.RED);
		} else {
			this.chart.setBackground(Color.WHITE);

		}

		hit = true;

	}

	public Ship getLinkedShip() {
		return linkedShip;
	}

	public Ship getChoosenShip() {
		return choosenShip;
	}

	public void setChoosenShip(Ship choosenShip) {
		this.choosenShip = choosenShip;
	}

	public void setShipToPut(Ship shipToPut) {
		this.linkedShip = shipToPut;
	}

}
