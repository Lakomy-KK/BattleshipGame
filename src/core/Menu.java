package core;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame {

	private JPanel contentPane;
	static Menu frame;
	static Player player;
	static Robot robot;

	/**
	 * Uruchomienie gry
	 */
	static public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player = new Player();
					robot = new Robot();
					robot.putShipsToChart();
					dependencyBeetweenPlayerAndRobot();
					View view = new View(player, robot);
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Ustawienie zale¿noœci pomiedzy robotem i graczem
	private static void dependencyBeetweenPlayerAndRobot() {
		robot.enemy = player;
		player.enemy = robot;
		player.fillEnemyChart();
		robot.fillEnemyChart();
		player.setPlayerControlOnEnemyChart();

	}

}
