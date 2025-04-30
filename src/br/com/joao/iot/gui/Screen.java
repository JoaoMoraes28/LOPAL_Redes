package br.com.joao.iot.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import br.com.joao.iot.model.Calculator;
import br.com.joao.iot.model.Ip;

public class Screen {
	
	// Variables of class
	private JTextField textIp;
	private JLabel labelIp;
	private JButton buttonCalc;
	private JScrollPane scroll;
	private JList listIp;
	private JScrollPane error;
	
	
	public void createScreen() {
		
		// Configures of screen
		JFrame screen = new JFrame();
		screen.setSize(500, 700);
		screen.setTitle("Calculadora de Redes");
		screen.setResizable(false);
		screen.setLayout(null);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Container for add components
		Container container = screen.getContentPane();
		
		// Creating JLabel and determinate yours features
		labelIp = new JLabel();
		labelIp.setText("Insira o IP");
		labelIp.setBounds(220, 20, 60, 30);
		
		// Creating JTextField and determinate yours features
		textIp = new JTextField();
		textIp.setBounds(200, 60, 100, 30);
		
		// Creating JButton and determinate yours features
		buttonCalc = new JButton();
		buttonCalc.setText("Fornecer Dados");
		buttonCalc.setBounds(175, 120, 150, 30);
		
		// Creating JList
		listIp = new JList();
		
		// Creating JScrollPane and determinate yours features
		scroll = new JScrollPane(listIp);
		scroll.setBounds(50, 180, 400, 200);
		
		// Creating Listener of Button
		buttonCalc.addActionListener(new ActionListener() {
			
			// Anonymous function  
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Ip ip = new Ip();
				
				ip.setIp(textIp.getText());
				ip.extractCidr();
				ip.extractClas();
				
				Calculator calculator = new Calculator();
				
				// Passing Object IP for defineClass 
				calculator.defineIpClass(ip);
				
				// Printing result in JList
				String[] resultEnd = calculator.vectorResult(ip);
				listIp.setListData(resultEnd);
				
			}
		});
		
		// Add components in Screen
		container.add(textIp);	
		container.add(labelIp);
		container.add(buttonCalc);
		container.add(scroll);
		
		// Turned Screen visible
		screen.setVisible(true);
	}
	
}
