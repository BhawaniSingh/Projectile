package org.bhawanisingh.projectile;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class LaunchProjectile extends JFrame {

	private TestProjectile testProjectile;
	private Thread thread;
	private int WIDTH = 800;
	private int HEIGHT = 600;

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public LaunchProjectile() {
		super("Projectile Simulation");
		setSize(800, 600);
		testProjectile = new TestProjectile(this);
		add(testProjectile);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		thread = new Thread(testProjectile);
		thread.start();
	}

	public static void main(String[] args) {
		new LaunchProjectile();

	}
}
