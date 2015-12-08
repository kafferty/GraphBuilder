package com.lidochka.graph.events;

public class FunctionParamsEvent {
	
	private String notation;
	private double minX;
	private double maxX;
	private int numsOfPoints;

	public FunctionParamsEvent(String notation, double minX, double maxX, int width) {
		super();
		this.notation = notation;
		this.minX = minX;
		this.maxX = maxX;
		numsOfPoints = width;
	}
	public String getNotation() {
		return notation;
	}
	public double getMinX() {
		return minX;
	}
	public double getMaxX() {
		return maxX;
	}
	public int getNumsOfPoints() {return numsOfPoints;}

}
