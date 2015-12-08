package com.lidochka.graph.model;

import java.util.List;

public class FunctionData {

	private List<FunctionPoint> points;
	private String infixNotation;
	private double minScore;
	private double maxScore;
	
	public FunctionData (String infixNotation, List<FunctionPoint> points) {
		
		this.infixNotation = infixNotation;
		this.points = points;
		
		minScore = Double.MAX_VALUE;
		for (FunctionPoint point : points) {
			minScore = Math.min(minScore, point.getY());
		}
		if (Double.isInfinite(minScore))
			minScore=Double.MAX_VALUE*(-1);
		
		maxScore = Double.MIN_VALUE;
		for (FunctionPoint point : points) {
			maxScore = Math.max(maxScore, point.getY());
		}
		if (Double.isInfinite(maxScore))
			maxScore=Double.MAX_VALUE;
	}
	
	public String getInfixNotation() {
		return infixNotation;
	}

	public List<FunctionPoint> getPoints() {
		return points;
	}

	public int size() {
		return points.size();
	}
	
	public double getMinY() {
		return minScore;
	}

	public double getMaxY() {
		return maxScore;
	}
	
	public double getMinX() {
		return points.get(0).getX();
	}

	public double getMaxX() {
		return points.get(size()-1).getX();
	}
		
}
