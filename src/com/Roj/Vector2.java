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
	
	public void normalize()
	{
		double magnitude = magnitude();
		x = x / magnitude;
		y = y / magnitude;
	}
	
	public double magnitude()
	{
		return Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
	}
	
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y;
	}
	
}
