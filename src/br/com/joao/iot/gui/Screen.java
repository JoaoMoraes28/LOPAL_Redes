package br.com.joao.iot.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.joao.iot.model.Calculator;
import br.com.joao.iot.model.Ip;

public class Screen {
	
	private JTextField textIp;
	private JLabel labelIp;
	private JButton buttonCalc;
	private JLabel labelResult;
	
	
	public void createScreen() {
		
		JFrame screen = new JFrame();
		screen.setSize(500, 700);
		screen.setTitle("Calculadora de Redes");
		screen.setResizable(false);
		screen.setLayout(null);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = screen.getContentPane();

		labelIp = new JLabel();
		labelIp.setText("Insira o IP");
		labelIp.setBounds(220, 20, 60, 30);
		
		textIp = new JTextField();
		textIp.setBounds(200, 60, 100, 30);
		
		buttonCalc = new JButton();
		buttonCalc.setText("Fornecer Dados");
		buttonCalc.setBounds(175, 130, 150, 30);
		
		labelResult = new JLabel();
		labelResult.setBounds(125, 180, 200, 200);
		
		
		buttonCalc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Ip ip = new Ip();
				
				ip.setIp(textIp.getText());
				ip.extractCidr();
				ip.extractClas();
				
				Calculator calculator = new Calculator();
				calculator.defineIpClass(ip);
				
				labelResult.setText(ip.getClas());
				
			}
		});
		
		container.add(textIp);	
		container.add(labelIp);
		container.add(buttonCalc);
		container.add(labelResult);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		screen.setVisible(true);
	}
}
