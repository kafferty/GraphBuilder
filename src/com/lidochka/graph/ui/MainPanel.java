package com.lidochka.graph.ui;

import java.awt.*;
import java.awt.geom.GeneralPath;

import java.util.ArrayList;

import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


import com.lidochka.graph.events.FunctionDataEvent;
import com.lidochka.graph.model.FunctionData;
import com.lidochka.graph.model.FunctionPoint;
import com.lidochka.graph.service.AppEventListener;
@SuppressWarnings("serial")
public class MainPanel extends JPanel implements AppEventListener {

	private int padding = 25;
	private int labelPadding = 35;
	private Color lineColor = new Color(44, 102, 230, 180);
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2.0f);//,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER,20000.0f
	private int pointWidth = 4;
	private int numberYDivisions = 10;
	private FunctionData functionData = new FunctionData ("", new ArrayList<>());
	public MainPanel() {
		setPreferredSize(new Dimension(800, 600));
		setBorder(new EmptyBorder(10, 10, 10, 10));


	}




	@Override
	public void onApplicationEvent(Object event) {
		
		if (event instanceof FunctionDataEvent) {		
			FunctionDataEvent fdEvent = (FunctionDataEvent)event;
			setFunctionData (fdEvent.getFunctionData()) ;
		}
		
	}


	public void setFunctionData(FunctionData functionData) {
		this.functionData = functionData;
		invalidate();
		this.repaint();
	}



	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D graphics2 = (Graphics2D) g;
		graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// draw white background
		graphics2.setColor(Color.WHITE);

		graphics2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
		graphics2.setColor(Color.BLACK);

		List<FunctionPoint> functionPoints = functionData.getPoints();

		// create hatch marks and grid lines for y axis.
		if(functionData.getMinY() != functionData.getMaxY()) {
			for(int i = 0; i < numberYDivisions + 1; i++) {
				 int x0 = padding + labelPadding;
				 int x1 = pointWidth + padding + labelPadding;
				 int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
				 if (functionData.size() > 0) {
					 graphics2.setColor(gridColor);
					 graphics2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y0);
					 graphics2.setColor(Color.BLACK);
					 double val=  (functionData.getMinY()
							 + (functionData.getMaxY() - functionData.getMinY()) * ((i * 1.0) / numberYDivisions));
					 String yLabel= String.format("%.2g", val);
					 FontMetrics metrics = graphics2.getFontMetrics();
					 int labelWidth = metrics.stringWidth(yLabel);
					 graphics2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
				 }
				 graphics2.drawLine(x0, y0, x1, y0);
			 }
		}
		else
		{
			double step = functionData.getPoints().get(0).getY();
			double yUp = functionData.getPoints().get(0).getY();
			double yDown = functionData.getPoints().get(0).getY();
			String yLabel;
			FontMetrics metrics;
			int labelWidth;

			for (int i=0; i<= numberYDivisions/2; i++, yUp+=step, yDown-=step){
				graphics2.setColor(gridColor);

				graphics2.drawLine(padding + labelPadding,
						(int) ((this.getHeight() - labelPadding) / 2 - (this.getHeight() - 2 * padding - labelPadding) / 10.0 * i),
						this.getWidth() - padding,
						(int) ((this.getHeight() - labelPadding) / 2 - (this.getHeight() - 2 * padding - labelPadding) / 10.0 * i)
				);
				yLabel = yUp +"";
				metrics = graphics2.getFontMetrics();
				labelWidth = metrics.stringWidth(yLabel);
				graphics2.setColor(Color.BLACK);

				graphics2.drawString(yLabel, padding, (this.getHeight() - labelPadding)/2 - (this.getHeight() - 2*padding - labelPadding)/10*i+(metrics.getHeight() / 2) - 3);
				graphics2.setColor(gridColor);

				graphics2.drawLine(
						padding + labelPadding,
						(int)((this.getHeight() - labelPadding) / 2  + (this.getHeight() - 2 * padding - labelPadding) / 10.0*i),
						this.getWidth() - padding,
						(int)((this.getHeight() - labelPadding) / 2  + (this.getHeight() - 2 * padding - labelPadding) / 10.0*i) );
				yLabel = yDown +"";
				metrics = graphics2.getFontMetrics();
				labelWidth = metrics.stringWidth(yLabel);
				graphics2.setColor(Color.BLACK);

				graphics2.drawString(yLabel, padding, (this.getHeight() - labelPadding) / 2  + (this.getHeight() - 2 * padding - labelPadding) / 10*i+(metrics.getHeight() / 2)-3);
			}
		}

		// create hatch marks and grid lines for x axis
        double step = (functionData.size()-1)/10.0;
		for (double x = 0; (int)x < functionData.size(); x+=step) {
			if (functionData.size() > 1) {
					int x0 = (int)(x) * (getWidth() - padding * 2 - labelPadding) / (functionData.size() - 1) + padding + labelPadding;
					int y0 = getHeight() - padding - labelPadding;
					int y1 = y0 - pointWidth;
					graphics2.setColor(gridColor);
					graphics2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x0, padding);
					graphics2.setColor(Color.BLACK);
					String xLabel = String.format("%.2g",functionPoints.get((int) (x)).getX());
					FontMetrics metrics = graphics2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					graphics2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
					graphics2.drawLine(x0, y0, x0, y1);
			}
		}

		// create x and y axes
		graphics2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		graphics2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);
		
		//draw points
		List<Point> graphPoints = getGraphPoints (functionData);
		
		Stroke oldStroke = graphics2.getStroke();
		graphics2.setColor(lineColor);
		graphics2.setStroke(GRAPH_STROKE);
		GeneralPath p0 = new GeneralPath();
		p0.moveTo(graphPoints.get(0).getX(),graphPoints.get(0).getY());
		for (int i=1; i<graphPoints.size(); i++)
			p0.lineTo(graphPoints.get(i).getX(),graphPoints.get(i).getY());
		graphics2.draw(p0);
	}
	
	//Convert function points to the graph points
	private List<Point> getGraphPoints (FunctionData functionData) {
		
		double xScale = ((double) getWidth() - 2 * padding - labelPadding) / 
				(functionData.getMaxX() - functionData.getMinX());

		double yScale = ((double) getHeight() - 2 * padding - labelPadding);
		double tmp=
				( - functionData.getMinY());
		tmp+=functionData.getMaxY();
		yScale/=tmp;


		List<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < functionData.size(); i++) {
			int x1 = (int) ((functionData.getPoints().get(i).getX()- functionData.getMinX()) * xScale + padding + labelPadding);
			int y1=0;
			if (functionData.getMaxY() == functionData.getMinY())
				y1= (this.getHeight()-padding)/2;
			else
				y1 = (int) ((functionData.getMaxY() - functionData.getPoints().get(i).getY()) * yScale + padding);

			if (y1<padding)
				y1=padding;
			if (y1>this.getHeight()-padding-labelPadding)
				y1=this.getHeight()-padding-labelPadding;
			graphPoints.add(new Point(x1, y1));
		}
		
		return graphPoints;
		
	}

}
