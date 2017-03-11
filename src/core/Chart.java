package core;
import java.awt.Color;
import java.io.Serializable;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
 /**
  * Klasa odpowiedzialna za utworzenie plansz gracza i robota
  *  sk³adaj¹cych sie z wierszy i kolumn komórek klasy Cell
  * @author Krzysiek
  *
  */

public class Chart {
	
	
	private Cell [][] cells=new Cell[10][10];
	  
	public Cell[][] getCells() {
		return cells;
	}



	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}


	//flaga dla planszy albo gracza albo robota
	public Chart(boolean isPlayer){
		if(isPlayer){
		initializePlayerChart();
		}else{
		initializeRobotChart();
		}
	}
	
	
	//tworzenie planszy robota
	private void initializeRobotChart() {
		int x0=40;
		int y0=40;
		int width=40;
		int height=40;
		/**
		 * inicjalizacja tablicy z kwadratem o 40
		 */
		JTable table[][]=new JTable[10][10];
		for(int i=0;i<10;i++){
			x0=40;
			for(int j=0;j<10;j++){
				
				table[i][j]= new JTable();				
				table[i][j].setBounds(x0,y0,width,height);
				table[i][j].setBackground(Color.GRAY);
				table[i][j].setBorder(new LineBorder(new Color(0,0,0)));
				cells[i][j]=new Cell(table[i][j]);
				
				x0=x0+40;
			}
			y0=y0+40;					
		}
		
		/**
		 * powiazanie komorek przed i po wzdluz kolumn i wzdluz wierszy
		 */
		for(int i=0;i<10;i++){
		     for(int j=0;j<10;j++){
		    	 if(i==0){
		    		 cells[i][j].setPreviousCellInColumn(null);
		    		 
		    	 }else{
		    		 cells[i][j].setPreviousCellInColumn(cells[i-1][j]);
		    	 }
		    	 if(j==0){
		    		 cells[i][j].setPreviousCellInRow(null);
		    		 
		    	 }else{
		    		 cells[i][j].setPreviousCellInRow(cells[i][j-1]);
		    	 }
		    	if(j==9 && i!=9){
		    		cells[i][j].setNextCellInRow(null);
		    		cells[i][j].setNextCellInColumn(cells[i+1][j]);
		    	}else if(j!=9 && i==9){
		    		cells[i][j].setNextCellInRow(cells[i][j+1]);
		    		cells[i][j].setNextCellInColumn(null);
		    	}else if(j==9 && i==9){
		    		cells[i][j].setNextCellInRow(null);
		    		cells[i][j].setNextCellInColumn(null);
		    	}else{
		    		cells[i][j].setNextCellInRow(cells[i][j+1]);
		    		cells[i][j].setNextCellInColumn(cells[i+1][j]);
		    	}
		    	 
		    	 
			}
			
		}
		
	}


	// to samo dla planszy gracza
	private void initializePlayerChart(){
		
		int x0=600;
		int y0=40;
		int width=40;
		int height=40;
		JTable table[][]=new JTable[10][10];
		for(int i=0;i<10;i++){
			x0=600;
			for(int j=0;j<10;j++){
				
				table[i][j]= new JTable();				
				table[i][j].setBounds(x0,y0,width,height);
				table[i][j].setBackground(Color.GRAY);
				table[i][j].setBorder(new LineBorder(new Color(0,0,0)));
				cells[i][j]=new Cell(table[i][j]);
				x0=x0+40;
			}
			y0=y0+40;					
		}
		
		//stworzenie komorek i powiazanie danej komorki z nastepnikiem
		//pierwszy wiersz nie ma powiazania na komorkach na obwodzie planszy
		for(int i=0;i<10;i++){
		     for(int j=0; j<10; j++){
		    	if(j==9 && i != 9){
		    		cells[i][j].setNextCellInRow(null);
		    		cells[i][j].setNextCellInColumn(cells[i+1][j]);
		    	}else if(j!=9 && i==9){
		    		cells[i][j].setNextCellInRow(cells[i][j+1]);
		    		cells[i][j].setNextCellInColumn(null);
		    	}else if(j==9 && i==9){
		    		cells[i][j].setNextCellInRow(null);
		    		cells[i][j].setNextCellInColumn(null);
		    	}else{
		    		cells[i][j].setNextCellInRow(cells[i][j+1]);
		    		cells[i][j].setNextCellInColumn(cells[i+1][j]);
		    	}
		    	 
		    	 
			}
			
		}
		
		
	}
	//ustawienie powi¹zania komórek z danym statkiem
	public void setCurrentShipForEachCell(Ship ship){
		
		 for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				cells[i][j].setChoosenShip(ship);
			}
		 }
		
	}
	
	public void initialTheCurrentShipForEachCell(){
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				cells[i][j].setChoosenShip(null);
			}
		 }
	}
	//zerowanie planszy ze statkows
	public void resetShipPlacementOnEachSlot(){
	   for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				cells[i][j].setChoosenShip(null);
				cells[i][j].setShipToPut(null);
				cells[i][j].getChart().setBackground(Color.GRAY);
			
			}
		 }
   }
}



