package com.lidochka.graph.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.IllegalFormatException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.lidochka.graph.events.ErrorEvent;
import com.lidochka.graph.events.FunctionParamsEvent;
import com.lidochka.graph.service.AppEventListener;
import com.lidochka.graph.service.EventsBus;
import com.lidochka.graph.service.ParserExpression.Operation.BinaryFunction.Exp;


public class ControlPanel extends JPanel implements AppEventListener{

	private static final String BTN_TXT = "Draw";
	JTextField functionText, xMinText, xMaxText, yMinText, yMaxText;

	private  String changeSeparator (String string){
		for (int i=0; i<string.length(); i++)
			if(string.charAt(i) == ','){
				String[] tmp = string.split(",");
				string = tmp[0]+"."+tmp[1];
			}
		return string;
	}
	public ControlPanel() {

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(800, 50));
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel fNlabel = new JLabel("Function: ");
		fNlabel.setFont(this.getFont());
		add(fNlabel);

		functionText = new JTextField("sin(x/4); x^2; log(x); 1/x",15);
		functionText.setFont(this.getFont());
		functionText.setBackground(Color.WHITE);
		functionText.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				functionText.setBackground(Color.white);
				functionText.repaint();
			}
		});
		add(functionText);

		JLabel xMinlabel = new JLabel("Min X :");
		xMinlabel.setFont(this.getFont());
		add(xMinlabel);

		xMinText = new JTextField("00.00",6);
		xMinText.setFont(this.getFont());
		xMinText.setBackground(Color.WHITE);
		xMinText.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clearParam();
			}
		});
		add(xMinText);

		JLabel xMaxlabel = new JLabel("Max X :");
		xMaxlabel.setFont(this.getFont());
		add(xMaxlabel);

		xMaxText = new JTextField("50.00",6);
		xMaxText.setFont(this.getFont());
		xMaxText.setBackground(Color.WHITE);
		xMaxText.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clearParam();
			}
		});
		add(xMaxText);

		JLabel yMinlabel = new JLabel("Min Y :");
		yMinlabel.setFont(this.getFont());
		add(yMinlabel);

		yMinText = new JTextField("-10.00",6);
		yMinText.setFont(this.getFont());
		yMinText.setBackground(Color.WHITE);
		yMinText.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clearParam();
			}
		});
		add(yMinText);

		JLabel yMaxLabel = new JLabel("Max Y :");
		yMaxLabel.setFont(this.getFont());
		add(yMaxLabel);

		yMaxText = new JTextField("10.00",6);
		yMaxText.setFont(this.getFont());
		yMaxText.setBackground(Color.WHITE);
		yMaxText.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clearParam();
			}
		});
		add(yMaxText);

		JButton button = new JButton(BTN_TXT);


		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(BTN_TXT)) {
					try {
						String[] functionNotations = functionText.getText().trim().split(";");

						double minX = Double.valueOf(changeSeparator(xMinText.getText().trim()));
						double maxX = Double.valueOf(changeSeparator(xMaxText.getText().trim()));
						double minY = Double.valueOf(changeSeparator(yMinText.getText().trim()));
						double maxY = Double.valueOf(changeSeparator(yMaxText.getText().trim()));
						if(minX>=maxX || minY>=maxY)
							throw new IllegalArgumentException();
						EventsBus.publishEvent(new FunctionParamsEvent(functionNotations, minX, maxX, minY, maxY, (getWidth() - 85)));
					}
					catch (Exception exp){
						EventsBus.publishEvent(new ErrorEvent(ErrorEvent.PARAMS_ERROR));
					}
				}
			}
		});

		add(button);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getWidth()));
	}
	private void clearParam(){
		xMaxText.setBackground(Color.white);
		xMinText.setBackground(Color.white);
		yMinText.setBackground(Color.white);
		yMaxText.setBackground(Color.white);
		xMaxText.repaint();
		xMinText.repaint();
		yMaxText.repaint();
		yMinText.repaint();
	}
	@Override
	public void onApplicationEvent(Object event) {

		if(event instanceof ErrorEvent){
			ErrorEvent error= (ErrorEvent)event;
			switch (error.getType()){
				case ErrorEvent.FUNC_ERROR:
					functionText.setBackground(new Color(1f,0,0,0.5f));
					break;
				default:
					xMaxText.setBackground(new Color(1f, 0, 0, 0.5f));
					xMinText.setBackground(new Color(1f, 0, 0, 0.5f));
					yMaxText.setBackground(new Color(1f, 0, 0, 0.5f));
					yMinText.setBackground(new Color(1f, 0, 0, 0.5f));
			}
			super.repaint();
		}
		if(event instanceof FunctionParamsEvent){
			xMinText.setText(String.format("%.2g",((FunctionParamsEvent) event).getMinX()));
			xMaxText.setText(String.format("%.2g",((FunctionParamsEvent) event).getMaxX()));
			yMinText.setText(String.format("%.2g",((FunctionParamsEvent) event).getMinY()));
			yMaxText.setText(String.format("%.2g",((FunctionParamsEvent) event).getMaxY()));

		}

	}

}