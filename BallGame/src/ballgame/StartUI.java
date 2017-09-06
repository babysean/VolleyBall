/*
 * 2017 04 01
 * Game first UI
 * */
package ballgame;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartUI extends Frame implements ActionListener {
	private JPanel totalPanel, upPanel, downPanel;
	private JLabel titleLabel;
	private JButton startButton;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start")) {
			// if start pushed , move game UI
			new GameUI();
			dispose();
		}
	}

	// UI Setting
	public void initLay() {

		 totalPanel = new JPanel(new GridLayout(2, 1));
		 upPanel = new JPanel();
		 downPanel = new JPanel();
		
		 titleLabel = new JLabel("Baaaaaaaaaall");
		 startButton = new JButton("Start");
		 startButton.addActionListener(this);
		
		 upPanel.add(titleLabel);
		 downPanel.add(startButton);
		
		 totalPanel.add(upPanel);
		 totalPanel.add(downPanel);
		
		 add(totalPanel);
	}

	// Constructor
	public StartUI() {
		initLay();

		// if clicked close Button, close StartUI
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		this.setVisible(true);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(800, 600);
		this.setLocation(new Point((d.width / 2) - (this.getWidth() / 2), (d.height / 2) - (this.getHeight() / 2)));
	}

	public static void main(String[] args) {
		new StartUI();
	}
}
