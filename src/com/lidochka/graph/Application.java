package com.lidochka.graph;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.lidochka.graph.events.FunctionParamsEvent;
import com.lidochka.graph.service.EventsBus;
import com.lidochka.graph.service.FunctionParserImpl;
import com.lidochka.graph.ui.ControlPanel;
import com.lidochka.graph.ui.MainPanel;

import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class Application {
	
	private FunctionParserImpl functionParser = new FunctionParserImpl();
	private MainPanel mainPanel;
	private ControlPanel controlPanel;
	
	protected void showUI () {
		 		
 		JFrame frame = new JFrame("AOperation plotter");
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		
		controlPanel = new ControlPanel();
		mainPanel = new MainPanel();
		EventsBus.subscribe(functionParser);
		EventsBus.subscribe(mainPanel);
		EventsBus.subscribe(controlPanel);
 		
 		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
 		
 		//Setup example data
 		mainPanel.setFunctionData(functionParser.parseFunction("sin(x/4)", 0.0d, 50.0d, 720)); ;
 		
 		frame.getContentPane().add(mainPanel);
 		frame.getContentPane().add(controlPanel);
 		
 		frame.pack();
 		frame.setLocationRelativeTo(null);
 		frame.setVisible(true);
		frame.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				//EventsBus.publishEvent(new FunctionParamsEvent(functionNotation,minX, maxX, getWidth()-75));
			}
		});
 		
	}

    public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
	         
			public void run() {
				Application app = new Application();
				app.showUI();
	        }
			
	    });
	}
}
