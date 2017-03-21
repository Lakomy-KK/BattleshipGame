package core;

import java.awt.Font;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Rysowanie panelu gry.
 * (Ustawienia koordynatów plansz s¹ zapisane w klasie Chart)
 * @author Krzysiek
 *
 */

public class View extends JFrame {

	public JPanel contentPane;
	public JComboBox<String> ships = new JComboBox<>();

	Menu backToMenu;

	Player player;
	Robot robot;

	public View(Player player, Robot robot) {

		this.player = player;
		player.setCombo(ships);
		this.robot = robot;

		setPanelFrame();
		setChartsOnPanel();
		setButtonsOnPanel();
		setLabelsOnPanel();

	}
	/**
	 * Dodanie elementow do panelu gry.
	 */
	private void setLabelsOnPanel() {

		JLabel lblRobot = new JLabel("Robot");
		lblRobot.setFont(new Font("Helvetica", Font.BOLD | Font.ITALIC, 52));
		lblRobot.setBounds(80, 495, 380, 81);
		contentPane.add(lblRobot); //dodanie labela robota

		JLabel lblPlayer = new JLabel("Player");
		lblPlayer.setFont(new Font("Helvetica", Font.BOLD | Font.ITALIC, 53));
		lblPlayer.setBounds(748, 487, 193, 81);
		contentPane.add(lblPlayer);

	}

	private void setChartsOnPanel() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				contentPane.add(player.getChart().getCells()[i][j].getChart());
				contentPane.add(robot.getChart().getCells()[i][j].getChart());

			}
		}

	}

	private void setPanelFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BattleShip Game");
		setBounds(50, 0, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}

	private void setButtonsOnPanel() {
		// ustawienia buttonow na panelu
		//domyslne ustawienie combo na wszystkie mozliwe statki
		for (Map.Entry<String, Ship> entry : player.getShips().entrySet()) {

			ships.addItem(entry.getValue().getName());
		}
		
		ships.setBounds(1050, 70, 200,40);
		contentPane.add(ships);
		
		player.rotate.setBounds(1050, 132, 200, 100);
		contentPane.add(player.rotate);
		
		player.start.setBounds(700, 600, 200, 48);
		contentPane.add(player.start);

		player.reset.setBounds(1000, 600, 200, 48);
		contentPane.add(player.reset);
		
		

	}
}
