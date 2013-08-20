package org.bhawanisingh.projectile;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class LaunchProjectile extends JFrame {

	private TestProjectile testProjectile;
	private Thread thread;
	private int w = 800;
	private int h = 600;

	public LaunchProjectile() {
		super("Projectile Simulation");
		setSize(w, h);
		testProjectile = new TestProjectile(w, h);
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