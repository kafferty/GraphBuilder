package com.lidochka.graph.model;

import java.util.List;

public class FunctionData {

	private List<FunctionPoint>[] points;
	private String[] infixNotations;
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	
	public FunctionData (String[] infixNotation, List<FunctionPoint>[] points, double minX, double maxX, double minY, double maxY) {
		this.infixNotations = infixNotation;
		this.points = points;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}
	
	public String[] getInfixNotations() {
		return infixNotations;
	}

	public List<FunctionPoint>[] getPoints() {
		return points;
	}

	public int size() {
		return points.length;
	}
	
	public double getMinY() {
		return minY;
	}

	public double getMaxY() {
		return maxY;
	}
	
	public double getMinX() {
		return minX;
	}

	public double getMaxX() {
		return maxX;
	}
		
}
