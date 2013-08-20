package org.bhawanisingh.projectile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

public class Ball {
	final float dash1[] = { 10.0f, 5.0f, 2.0f, 5.0f };
	final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 10.0f, dash1, 0.0f);

	private int radius = 10;
	private int x = 10;
	private int previousX = x;
	private int y = 10;
	private int previousY = y;
	private double rotate = 0.0d;

	private TestProjectile testProjectile;
	private ArrayList<Integer> xPointsList = new ArrayList<Integer>();
	private ArrayList<Integer> yPointsList = new ArrayList<Integer>();

	public Ball(TestProjectile testProjectile) {
		this.testProjectile = testProjectile;
	}

	public void renderBall(Graphics2D graphics2d) {
		graphics2d.setStroke(dashed);
		graphics2d.drawPolyline(arrayListToIntArray(xPointsList), arrayListToIntArray(yPointsList), xPointsList.size());

		graphics2d.rotate(rotate, x, y);
		graphics2d.setColor(Color.GREEN);

		graphics2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		graphics2d.setColor(Color.BLACK);
		graphics2d.drawLine(x, y, (x - ((radius * 3) / 4)), (y - ((radius * 3) / 4)));
		graphics2d.drawLine(x, y, (x - ((radius * 3) / 4)), (y - ((radius * 3) / 4)));

		graphics2d.setStroke(dashed);
		graphics2d.setColor(Color.MAGENTA);
		graphics2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	public void parabolicMotion() {
		double verticalVelocity = testProjectile.calculateVerticalHeight();
		x = (int) (testProjectile.calculateHorizontalDistance() + radius);
		if (verticalVelocity <= 0) {
			verticalVelocity = -verticalVelocity;
		}
		y = (int) ((testProjectile.getHeight() - verticalVelocity) - radius);
		addPointToArrayList();
//		y = y - radius;
		calculateRotationAngle();
	}

	public void calculateRotationAngle() {
		double difference = Math.abs(previousX - x);
		double radians = (difference * Math.PI) / 20;
		rotate = rotate + radians;
		previousX = x;
	}

	public void addPointToArrayList() {
		xPointsList.add(x);
		yPointsList.add(y);
	}

	public int[] arrayListToIntArray(ArrayList<Integer> points) {
		int[] intPoints = new int[points.size()];
		Iterator<Integer> iterator = points.iterator();
		for (int i = 0; i < intPoints.length; i++) {
			intPoints[i] = iterator.next().intValue();
		}
		return intPoints;
	}

	public int getRadius() {
		return radius;
	}
}