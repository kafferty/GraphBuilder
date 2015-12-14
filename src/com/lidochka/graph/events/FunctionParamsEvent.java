package com.lidochka.graph.events;

public class FunctionParamsEvent {
	
	private String [] notations;
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private int numsOfPoints;

	public FunctionParamsEvent(String[] notations, double minX, double maxX, double minY, double maxY, int width) {
		super();
		this.notations = notations;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		numsOfPoints = width;
	}
	public String[] getNotations() {
		return notations;
	}
	public double getMinX() {
		return minX;
	}
	public double getMaxX() {
		return maxX;
	}
	public double getMinY() { return minY; }
	public double getMaxY() { return maxY; }
	public int getNumsOfPoints() {return numsOfPoints;}

}
