package com.lidochka.graph.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.lidochka.graph.events.ErrorEvent;
import com.lidochka.graph.events.FunctionParamsEvent;
import com.lidochka.graph.service.AppEventListener;
import com.lidochka.graph.service.EventsBus;


public class ControlPanel extends JPanel implements AppEventListener{
	
	private static final String BTN_TXT = "Draw";
	
	private static final Font font = new Font("Helvetica", Font.ROMAN_BASELINE, 15);
	JTextField functionText,minText,maxText;

	public ControlPanel() {

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(800, 50));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel fNlabel = new JLabel("Function: ");
		fNlabel.setFont(font);          
		add(fNlabel);
		
		functionText = new JTextField("sin(x/4)",15);
		functionText.setFont(font);
		functionText.setBackground(Color.WHITE);
functionText.addMouseListener(new MouseAdapter() {
@Override
public void mousePressed(MouseEvent e) {
functionText.setBackground(Color.WHITE);
}
});
		add(functionText);

		JLabel minlabel = new JLabel("Min X :");
		minlabel.setFont(font);          
		add(minlabel);
		
		minText = new JTextField("00.00",6);
		minText.setFont(font);
		minText.setBackground(Color.WHITE);
		minText.addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
		minText.setBackground(Color.WHITE);
		}
		});
		add(minText);
		
		JLabel maxlabel = new JLabel("Max X :");
		fNlabel.setFont(font);          
		add(maxlabel);
		
		maxText = new JTextField("50.00",6);
		maxText.setFont(font);
		maxText.setBackground(Color.WHITE);
		maxText.addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
		maxText.setBackground(Color.WHITE);
		}
		});
		add(maxText);
		
		JButton button = new JButton(BTN_TXT);
		
		button.addActionListener(new ActionListener() {
			 
			public void actionPerformed(ActionEvent e) {
			    if (e.getActionCommand().equals(BTN_TXT)) {
			    	
			    	String functionNotation = functionText.getText().trim();
			    	
			    	//TODO add validation
			    	double minX = Double.valueOf(minText.getText().trim()) ;
			    	double maxX = Double.valueOf(maxText.getText().trim()) ;
			    	EventsBus.publishEvent(new FunctionParamsEvent(functionNotation,minX, maxX, getWidth()-90));
			    }
			  }
        });  
		
		add(button);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getWidth()));
	}

	@Override
	public void onApplicationEvent(Object event) {

	if(event instanceof ErrorEvent){
	ErrorEvent error= (ErrorEvent)event;
	switch (error.getType()){
		case 1:
		functionText.setBackground(new Color(1f,0,0,0.5f));
	break;
		case 2:
		minText.setBackground(new Color(1f,0,0,0.5f));
	break;
	default:
	maxText.setBackground(new Color(1f, 0, 0, 0.5f));

			}
				super.repaint();
	}
	}
}
