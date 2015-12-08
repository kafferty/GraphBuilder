package com.lidochka.graph;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.lidochka.graph.events.FunctionParamsEvent;
import com.lidochka.graph.service.EventsBus;
import com.lidochka.graph.service.FunctionParserImpl;
import com.lidochka.graph.ui.ControlPanel;
import com.lidochka.graph.ui.MainPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Application {

	private FunctionParserImpl functionParser = new FunctionParserImpl();
	private MainPanel mainPanel;
	private ControlPanel controlPanel;

	public void save()
	{
		JFileChooser fileChooser = new JFileChooser();
		if ( fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
			BufferedImage bImg = new BufferedImage(mainPanel.getWidth(), mainPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D cg = bImg.createGraphics();
			mainPanel.paintAll(cg);
			try {
				if (ImageIO.write(bImg, "png", new File(fileChooser.getSelectedFile()+".png"))) {
					System.out.println("— saved");
				}
			} catch (IOException e) {
// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void showUI () {
		 		
 		JFrame frame = new JFrame("Graphic builder");
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		
		controlPanel = new ControlPanel();
		mainPanel = new MainPanel();
		EventsBus.subscribe(functionParser);
		EventsBus.subscribe(mainPanel);
		EventsBus.subscribe(controlPanel);
 		
 		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
 		
 		//Setup example data
 		mainPanel.setFunctionData(functionParser.parseFunction("sin(x/4)", 0.0d, 50.0d, 720)); ;
		JMenuBar menuBar = new JMenuBar();
		JMenu menuMain = new JMenu("Main");
		JMenuItem item1 = new JMenuItem("Save");
		JMenuItem item2 = new JMenuItem("Exit");


        item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});


		menuBar.add(menuMain);
		menuMain.add(item1);
		menuMain.add(item2);

		frame.setJMenuBar(menuBar);
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
