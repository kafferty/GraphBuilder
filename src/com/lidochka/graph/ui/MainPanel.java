package com.lidochka.graph.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


import com.lidochka.graph.events.FunctionDataEvent;
import com.lidochka.graph.events.FunctionParamsEvent;
import com.lidochka.graph.model.FunctionData;
import com.lidochka.graph.model.FunctionPoint;
import com.lidochka.graph.service.AppEventListener;
import com.lidochka.graph.service.EventsBus;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements AppEventListener {

	private int padding = 25;
	private int leftLabelPadding = 35;
	private int bottomLabelPadding = 35;
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	private int numberYDivisions = 10;
	private int numberXDivisions = 10;
	private FunctionData functionData = new FunctionData(new String[0], new ArrayList[0], 0, 0, 0, 0);
	private Point press = new Point(0, 0), mouseLoc = new Point(0, 0);
	private Rectangle2D rectangle = new Rectangle2D.Double(0, 0, 0, 0);
	private boolean btnPress = false;
	double xScale = 0, yScale = 0;

	public void setRectangle() {
		if (!press.equals(mouseLoc))
			if (mouseLoc.y >= press.y)
				if (press.x <= mouseLoc.getX())
					rectangle = new Rectangle2D.Double(press.x, press.y, mouseLoc.x - press.x, mouseLoc.y - press.y);
				else
					rectangle = new Rectangle2D.Double(mouseLoc.x, press.y, press.x - mouseLoc.x, mouseLoc.y - press.y);
			else if (press.x <= mouseLoc.getX())
				rectangle = new Rectangle2D.Double(press.x, mouseLoc.y, mouseLoc.x - press.x, press.y - mouseLoc.y);
			else
				rectangle = new Rectangle2D.Double(mouseLoc.x, mouseLoc.y, press.x - mouseLoc.x, press.y - mouseLoc.y);
	}

	public MainPanel() {
		setPreferredSize(new Dimension(800, 600));
		setBorder(new EmptyBorder(10, 10, 10, 10));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getX() > padding + leftLabelPadding &&
						e.getX() < getWidth() - padding &&
						e.getY() < getHeight() - padding - bottomLabelPadding &&
						e.getY() > padding) {
					press = e.getPoint();
					btnPress = true;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				btnPress = false;
				Point2D A = toFuncPoint(new Point2D.Double(rectangle.getMinX(), rectangle.getMinY()));
				Point2D B = toFuncPoint(new Point2D.Double(rectangle.getMaxX(), rectangle.getMaxY()));
				if (rectangle.getHeight() != 0 && rectangle.getWidth() != 0)
					EventsBus.publishEvent(new FunctionParamsEvent(functionData.getInfixNotations(),
							A.getX(), B.getX(),
							B.getY(), A.getY(),
							getWidth() - leftLabelPadding - 2 * padding));
				repaint();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (e.getX() > leftLabelPadding + padding && e.getX() < getWidth() - padding)
					mouseLoc.x = e.getX();
				else if (e.getX() <= padding + leftLabelPadding)
					mouseLoc.x = padding + leftLabelPadding;
				else
					mouseLoc.x = getWidth() - padding;
				if ((e.getY() < (getHeight() - padding - bottomLabelPadding)) && (e.getY() > padding))
					mouseLoc.y = e.getY();
				else if (e.getY() < padding)
					mouseLoc.y = padding;
				else
					mouseLoc.y = getHeight() - padding - bottomLabelPadding;

				setRectangle();
				repaint();
			}
		});
		addMouseWheelListener((MouseWheelEvent e) -> {
			Point2D mouse = e.getPoint();
			mouse = toFuncPoint(mouse);
			float zoom = 0.1f;
			if (e.getWheelRotation() == 1)
				EventsBus.publishEvent(new FunctionParamsEvent(functionData.getInfixNotations(),
						functionData.getMinX() - (mouse.getX() - functionData.getMinX()) * zoom,
						functionData.getMaxX() + (functionData.getMaxX() - mouse.getX()) * zoom,
						functionData.getMinY() - (mouse.getY() - functionData.getMinY()) * zoom,
						functionData.getMaxY() + (functionData.getMaxY() - mouse.getY()) * zoom,
						getWidth() - leftLabelPadding - 2 * padding));
			else
				EventsBus.publishEvent(new FunctionParamsEvent(functionData.getInfixNotations(),
						functionData.getMinX() + (mouse.getX() - functionData.getMinX()) * zoom,
						functionData.getMaxX() - (functionData.getMaxX() - mouse.getX()) * zoom,
						functionData.getMinY() + (mouse.getY() - functionData.getMinY()) * zoom,
						functionData.getMaxY() - (functionData.getMaxY() - mouse.getY()) * zoom,
						getWidth() - leftLabelPadding - 2 * padding));
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				EventsBus.publishEvent(new FunctionParamsEvent(functionData.getInfixNotations(),
						functionData.getMinX(), functionData.getMaxX(),
						functionData.getMinY(), functionData.getMaxY(),
						getWidth() - leftLabelPadding - 2 * padding));
			}
		});
	}

	@Override
	public void onApplicationEvent(Object event) {

		if (event instanceof FunctionDataEvent) {
			FunctionDataEvent fdEvent = (FunctionDataEvent) event;
			setFunctionData(fdEvent.getFunctionData());
		}

	}

	public void setFunctionData(FunctionData functionData) {
		this.functionData = functionData;
		invalidate();
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {

		xScale = ((double) getWidth() - 2 * padding - leftLabelPadding) /
				(functionData.getMaxX() - functionData.getMinX());
		yScale = ((double) getHeight() - 2 * padding - bottomLabelPadding) /
				(functionData.getMaxY() - functionData.getMinY());

		super.paintComponent(g);
		Graphics2D graphics2 = (Graphics2D) g;
		graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		// draw white background
		graphics2.setColor(Color.WHITE);
		graphics2.fillRect(padding + leftLabelPadding, padding, getWidth() - (2 * padding) - leftLabelPadding, getHeight() - 2 * padding - bottomLabelPadding);
		graphics2.setColor(Color.BLACK);


		// create hatch marks and grid lines for y axis.

		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + leftLabelPadding;
			int y0 = getHeight() - ((i * (getHeight() - padding * 2 - bottomLabelPadding)) / numberYDivisions
					+ padding + bottomLabelPadding);
			int x1 = padding + leftLabelPadding + 4;
			graphics2.setColor(gridColor);
			graphics2.drawLine(padding + leftLabelPadding + 1 + 4, y0, getWidth() - padding, y0);
			graphics2.setColor(Color.BLACK);
			double val = (functionData.getMinY()
					+ (functionData.getMaxY() - functionData.getMinY()) * ((i * 1.0) / numberYDivisions));
			String yLabel = String.format("%.2g", val);
			FontMetrics metrics = graphics2.getFontMetrics();
			int labelWidth = metrics.stringWidth(yLabel);
			graphics2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);

			graphics2.drawLine(x0, y0, x1, y0);
		}


		// create hatch marks and grid lines for x axis

		for (int i = 0; i < numberXDivisions + 1; i++) {
			int x0 = i * (getWidth() - padding * 2 - leftLabelPadding) / numberXDivisions + padding + leftLabelPadding;
			int y0 = getHeight() - padding - bottomLabelPadding;
			int y1 = y0 - 4;
			graphics2.setColor(gridColor);
			graphics2.drawLine(x0, getHeight() - padding - bottomLabelPadding - 5, x0, padding);
			graphics2.setColor(Color.BLACK);
			double val = functionData.getMinX() + i * (functionData.getMaxX() - functionData.getMinX()) / numberXDivisions;
			String xLabel = String.format("%.2g", val);
			FontMetrics metrics = graphics2.getFontMetrics();
			int labelWidth = metrics.stringWidth(xLabel);
			graphics2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);

			graphics2.drawLine(x0, y0, x0, y1);
		}

		// create x and y axes
		graphics2.drawLine(padding + leftLabelPadding, getHeight() - padding - bottomLabelPadding, padding + leftLabelPadding, padding);
		graphics2.drawLine(padding + leftLabelPadding, getHeight() - padding - bottomLabelPadding, getWidth() - padding, getHeight() - padding - bottomLabelPadding);

		//draw points
		List<Point2D>[] graphPoints = getGraphPoints(functionData);
		for (int i = 0; i < graphPoints.length; i++) {
			List<Point2D> listPoint = graphPoints[i];
			if (listPoint.size() == 0)
				continue;
			int color = i + 1;
			graphics2.setColor(new Color((float) (color % 2), (float) (color / 2 % 2), (float) (color / 4 % 2)));
			graphics2.setStroke(GRAPH_STROKE);

			GeneralPath p0 = new GeneralPath();
			p0.moveTo(listPoint.get(0).getX(), listPoint.get(0).getY());

			for (int j = 1; j < listPoint.size(); j++) {
				if (listPoint.get(j).getX() - listPoint.get(j - 1).getX() <= 1.1) {
					p0.lineTo(listPoint.get(j).getX(), listPoint.get(j).getY());
				} else {
					graphics2.draw(p0);
					p0.moveTo(listPoint.get(j).getX(), listPoint.get(j).getY());
				}
			}
			graphics2.draw(p0);
		}

		//Обозначения графиков
		int x = 25, y = getHeight() - 15;
		for (int i = 0; i < functionData.size(); i++) {
			int color = i + 1;
			graphics2.setColor(new Color((float) (color % 2), (float) (color / 2 % 2), (float) (color / 4 % 2)));
			graphics2.drawLine(x, y, x + 20, y);
			graphics2.setColor(Color.black);
			graphics2.drawString(functionData.getInfixNotations()[i], x + 25, y + 5);
			x += 50 + functionData.getInfixNotations()[i].length() * 5;
		}
		//Выделение
		if (btnPress) {
			graphics2.setColor(new Color(0f, 0f, 1f, 0.2f));
			graphics2.fill(rectangle);
			graphics2.setColor(new Color(0.3f, 0.3f, 1f, 0.2f));
			graphics2.draw(rectangle);
		}


	}

	//Convert function points to the graph points
	private List<Point2D>[] getGraphPoints(FunctionData functionData) {
		List<Point2D>[] graphPoints = new List[functionData.size()];
		for (int i = 0; i < functionData.size(); i++) {
			graphPoints[i] = new ArrayList<>();
			List<FunctionPoint> pointList = functionData.getPoints()[i];
			for (int j = 0; j < pointList.size(); j++) {
				double x1 = ((pointList.get(j).getX() - functionData.getMinX()) * xScale + padding + leftLabelPadding);
				double y1 = (getHeight() - ((pointList.get(j).getY() - functionData.getMinY()) * yScale) - padding - bottomLabelPadding); //(int)( (getHeight()-bottomLabelPadding)/2.0- ((pointList.get(j).getY()) * yScale));

				if (y1 < padding)
					y1 = padding;
				if (y1 > this.getHeight() - padding - bottomLabelPadding)
					y1 = this.getHeight() - padding - bottomLabelPadding;
				graphPoints[i].add(new Point2D.Double(x1, y1));
			}
		}
		return graphPoints;
	}

	Point2D toFuncPoint(Point2D point) {
		point.setLocation((point.getX() - leftLabelPadding - padding) / xScale + functionData.getMinX(),
				(((getHeight() - (point.getY() + padding + bottomLabelPadding))) / yScale + functionData.getMinY()));
		return point;
	}

	public FunctionData getFunctionData() {
		return functionData;
	}

	public int getPadding() {
		return padding;
	}

	public int getLeftPadding() {
		return padding + leftLabelPadding;
	}

	public int getBottomPadding() {
		return padding + bottomLabelPadding;
	}
}