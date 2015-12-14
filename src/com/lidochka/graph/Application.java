package com.lidochka.graph;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.lidochka.graph.events.FunctionParamsEvent;
import com.lidochka.graph.model.FunctionData;
import com.lidochka.graph.service.EventsBus;
import com.lidochka.graph.service.FunctionParserImpl;
import com.lidochka.graph.ui.ControlPanel;
import com.lidochka.graph.ui.MainPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Application {

	private FunctionParserImpl functionParser = new FunctionParserImpl();
	private MainPanel mainPanel;
	private ControlPanel controlPanel;
	private static final Font font = new Font("Arial", Font.PLAIN, 16);

	private void save(JFrame frame)
	{
		FileDialog fileDialog = new FileDialog(frame,"Save",FileDialog.SAVE);
		fileDialog.setVisible(true);
		BufferedImage bImg = new BufferedImage(mainPanel.getWidth(), mainPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D cg = bImg.createGraphics();
		mainPanel.paintAll(cg);
		try {
			if (ImageIO.write(bImg, "png", new File(fileDialog.getFiles()[0]+".png"))) {
				System.out.println("-- saved");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void showHelp(Point loc){
		JFrame frame = new JFrame();
		JTextArea textArea = new JTextArea("Данная программа предназначена для построения графиков функций\n" +
				"Возможно построение: Sin, Cos, Tan, Abs (модуль), Log, e, Cot, Sqrt (корень)\n" + "Перечисление графиков через точку с запятой ");
		textArea.setEditable(false);
		frame.add(textArea);
		frame.setLocation(loc);
		frame.setSize(new Dimension(500,300));
		frame.setVisible(true);
	}
	protected void showUI () {

		JFrame frame = new JFrame("Graphic builder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		controlPanel = new ControlPanel();
		mainPanel = new MainPanel();
		EventsBus.subscribe(functionParser);
		EventsBus.subscribe(mainPanel);
		EventsBus.subscribe(controlPanel);

		mainPanel.setFont(font);
		controlPanel.setFont(font);

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		//Setup example data
		String[] functions = {"sin(x/4)","x^2","log(x)","1/x"};
		mainPanel.setFunctionData(functionParser.parseFunction(functions, 0d, 50d,-10d,10d, 715));
		JMenuBar menuBar = new JMenuBar();
		JMenu menuMain = new JMenu("Main");
		JMenuItem item1 = new JMenuItem("Save");
		JMenuItem item2 = new JMenuItem("Exit");
		JMenu help = new JMenu("Help");
		help.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showHelp(frame.getLocation());
			}
		});

		item1.addActionListener(e -> save(frame));
		item2.addActionListener(e -> System.exit(0));


		menuBar.add(menuMain);
		menuBar.add(help);
		menuMain.add(item1);
		menuMain.add(item2);

		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(mainPanel);
		frame.getContentPane().add(controlPanel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			Application app = new Application();
			app.showUI();
		});
	}
}
