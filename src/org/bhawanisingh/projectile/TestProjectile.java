package org.bhawanisingh.projectile;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;

public class TestProjectile extends JComponent implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {

	private double initialVelocity = 30;
	private double angle = 45;
	private double time = 0.0;
	private double deltaTime = 0.1;
	private double gravity = 9.81;
	private double horizontalDistance = calculateHorizontalDistance();
	private double horizontalvelocity;
	private double initialHorizontalVelocity;
	private double verticalHeight;
	private double verticalVelocity;
	private double initialVerticallVelocity;
	private double horizontalrange = calculateHorizontalRange();

	private int xPosition;
	private int yPosition;
	private int xTranslate = 0;
	private int yTranslate = 0;
	private int xTranslateAmount = 0;
	private int yTranslateAmount = 0;

	private Ball ball;

	private double scaleX = 1.0d;
	private double scaleY = 1.0d;
	private double scaleXAmount = 0.1d;
	private double scaleYAmount = (16 / 9) * scaleXAmount;

	private int h;
	private int w;

	public TestProjectile(int w, int h) {
		this.w = w;
		this.h = h;
		ball = new Ball(this);
		initialHorizontalVelocity = calculateInitialHorizontalVelocity();
		horizontalvelocity = initialHorizontalVelocity;

		initialVerticallVelocity = calculateInitialVerticalVelocity();
		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addComponentListener(this);
	}

	@Override
	public void run() {
		System.out.println("Angle : " + angle + "\t Angle (in Radians) : " + convertAngle());
		System.out.println("Time of Flight : " + calculateTimeOfFlight());
		System.out.println("Maximum Height : " + calculateMaximumHeightReached());
		System.out.println("Horizontal Range : " + horizontalrange);
		while (horizontalrange > horizontalDistance) {
			try {
				Thread.sleep(23);
			} catch (InterruptedException ex) {
			}
			this.repaint();
			ball.parabolicMotion();
			update();

		}
	}

	/**
	 * Method to paint all the components on to the screen
	 *
	 * @param g
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintComponents(g);
		g.dispose();
	}

	@Override
	public void paintComponents(Graphics g) {
		System.err.println("Height : " + h + "\tWidth : " + w);
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
//		graphics.setFont(new Font(this.getFont().getName(), Font.ITALIC, 50));
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		graphics.translate(ball.getRadius(), getHeight() - ball.getRadius());
		graphics.scale(scaleX, scaleY);
		graphics.translate(-ball.getRadius(), -getHeight() + ball.getRadius());
		graphics.translate(xTranslate, yTranslate);
		graphics.setStroke(new BasicStroke());
		graphics.drawLine(ball.getRadius(), 0, ball.getRadius(), getHeight() - ball.getRadius());
		graphics.drawLine(ball.getRadius(), getHeight() - ball.getRadius(), getWidth() + ball.getRadius(), getHeight() - ball.getRadius());
//		graphics.drawLine(0, 0, 0, h);
//		graphics.drawLine(0, h, w, h);
		ball.renderBall(graphics);
		// This method ensures that the display is up-to-date. It is useful for animation.
		Toolkit.getDefaultToolkit().sync();
		// Releases system resources and disposes graphics context
		g.dispose();
	}

	public void update() {

		if (horizontalrange >= horizontalDistance) {
			verticalVelocity = calculateVerticalVelocity();
			verticalHeight = calculateVerticalHeight();
//			System.out.println("Current Horizontal Distance : " + calculateHorizontalDistance());
//			System.out.println("Current Vertical Height : " + verticalHeight + "\tVertical Velocity : " + verticalVelocity);
			horizontalDistance = calculateHorizontalDistance();
			updateTime();
		}

	}

	//Horizontal Components
	/**
	 * Calculate and return initial horizontal velocity
	 * @return
	 */
	public double calculateInitialHorizontalVelocity() {
		double initialHorizontalVelocity;
		initialHorizontalVelocity = Math.cos(convertAngle()) * initialVelocity;
		return initialHorizontalVelocity;
	}

	/**
	 * Calculate and return horizontal distance
	 * @return
	 */
	public double calculateHorizontalDistance() {
		double hoizontalDistance;
		hoizontalDistance = horizontalvelocity * time;
		return hoizontalDistance;
	}

	// Vertical Components
	/**
	 * Calculate and return initial vertical velocity
	 * @return
	 */
	public double calculateInitialVerticalVelocity() {
		double initialVerticallVelocity;
		initialVerticallVelocity = Math.sin(convertAngle()) * initialVelocity;
		return initialVerticallVelocity;
	}

	/**
	 * Calculate and return vertical distance
	 * @return
	 */
	public double calculateVerticalHeight() {
		double verticalHeight;
		verticalHeight = (initialVerticallVelocity * time) - (0.5 * gravity * time * time);
		return verticalHeight;
	}

	/**
	 * Calculate and return vertical velocity
	 * @return
	 */
	public double calculateVerticalVelocity() {
		double verticalVelocity;
		verticalVelocity = initialVerticallVelocity - (gravity * time);
		return verticalVelocity;
	}

	//Other Components
	/**
	 * Calculate and return time of flight
	 * @return
	 */
	public double calculateTimeOfFlight() {
		double timeOfFlight;
		timeOfFlight = (2 * initialVelocity * Math.sin(convertAngle())) / gravity;
		return timeOfFlight;
	}

	/**
	 * Calculate and return maximum height
	 * @return
	 */
	public double calculateMaximumHeightReached() {
		double maximumHeightReached;
		maximumHeightReached = ((initialVelocity * initialVelocity * Math.sin(convertAngle()) * Math.sin(convertAngle())) / (2 * gravity));
		return maximumHeightReached;
	}

	/**
	 * Calculate and return horizontal range
	 * @return
	 */
	public double calculateHorizontalRange() {
		double horizontalRange;
		horizontalRange = (initialVelocity * initialVelocity * Math.sin(convertAngle() * 2)) / gravity;
		return horizontalRange;
	}

	/**
	 * Convert degrees to radians and return radians
	 * @return
	 */
	public double convertAngle() {
		double radianAngle;
		radianAngle = (angle * Math.PI) / 180;
		return radianAngle;
	}

	/**
	 * Update Time
	 * @param args
	 */
	// do stuff  
	public void updateTime() {
		time = time + deltaTime;
	}

	//Mouse Listeners
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
			scaleX = scaleX + (scaleX * scaleXAmount);
			scaleY = scaleY + (scaleY * scaleYAmount);
//			System.err.println("Scale  is : " + scale);
		} else {
			scaleX = Math.max(0.001, scaleX - (scaleX * scaleXAmount));
			scaleY = Math.max(0.001, scaleY - (scaleY * scaleYAmount));
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
//		if ((Math.abs(e.getX() - xPosition) <= 20) && (Math.abs(e.getX() - xPosition) <= 20)) {
		xTranslate = (int) ((((xTranslateAmount) + e.getX()) - xPosition) / scaleX);
		yTranslate = (int) ((((yTranslateAmount) + e.getY()) - yPosition) / scaleY);
//		} else {
//			xTranslate = (e.getX()) - xPosition;
//			yTranslate = (e.getY()) - yPosition;
//		}
		System.err.println("xTranslate : " + xTranslate + "\tyTranslate : " + yTranslate);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		xPosition = e.getX();
		yPosition = e.getY();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		xTranslateAmount = (int) (xTranslate * scaleX);
		yTranslateAmount = (int) (yTranslate * scaleY);

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println(e.getID());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
}