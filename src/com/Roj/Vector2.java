package com.Roj;

public class Vector2 {
	
	private double x;
	private double y;
	
	public Vector2(double x, double y)
	{
		this.setX(x);
		this.setY(y);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y;
	}
	
}
