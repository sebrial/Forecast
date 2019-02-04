package com.mercadolibre.model;

public class Planet {
	private final static int ZERO = 0;
	private final static int ONE = 1;
	private final static int THREESIXTY = 360;
	private double speed;
	private double angle;
	private double radius;
	private double x;
    private double y;

	public Planet(Double angle, Double radius, Double speed) {
		this.speed = speed;
		this.angle = angle;
		this.radius = radius;
		this.polarToCartesianCoordinates();
	}

	public void move() {
		Double newAngle = angle + speed * ONE;
//		while(newAngle < ZERO || newAngle >= THREESIXTY) {
//			if (newAngle >= THREESIXTY) {
//				newAngle = newAngle - THREESIXTY;
//			} else if( newAngle < ZERO) {
//				newAngle = newAngle + THREESIXTY;
//			}
//		}
		
		if (newAngle >= THREESIXTY) {
			newAngle = newAngle - (Math.floor(newAngle / THREESIXTY) * THREESIXTY);
		} else if (newAngle < ZERO) {
			newAngle = newAngle + ((Math.floor(Math.abs(newAngle) / THREESIXTY) + 1) * THREESIXTY);
		}
		
		this.setAngle(newAngle);
		this.polarToCartesianCoordinates();
		
	}
	
	private void polarToCartesianCoordinates (){
		this.x = radius * Math.cos(Math.toRadians(angle));
		this.y = radius * Math.sin(Math.toRadians(angle));

	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(Double angle) {
		this.angle = angle;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

}
