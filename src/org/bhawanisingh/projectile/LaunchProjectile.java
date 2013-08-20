package org.bhawanisingh.projectile;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class LaunchProjectile extends JFrame {

	private TestProjectile testProjectile;
	private Thread thread;
	private int width = 800;
	private int height = 600;

	public LaunchProjectile() {
		super("Projectile Simulation");
		setSize(width, height);
		testProjectile = new TestProjectile(width, height);
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
