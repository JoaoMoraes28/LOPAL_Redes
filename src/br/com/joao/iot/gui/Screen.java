package br.com.joao.iot.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import br.com.joao.iot.model.Ip;

public class Screen {

	// Variables of class
	private JTextField textIp;
	private JLabel labelIp;
	private JButton buttonCalc;
	private JScrollPane scroll;
	private JList listIp;
	private JLabel labelError;

	public void createScreen() {

		// Configures of screen
		JFrame screen = new JFrame();
		screen.setSize(500, 480);
		screen.setTitle("Calculadora de Redes");
		screen.setResizable(false);
		screen.setLayout(null);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Creating container for add components and Font's for elements
		Container container = screen.getContentPane();
		Font fontError = new Font("Arial", Font.ITALIC, 25);
		Font fontGeneral = new Font("Arial", Font.PLAIN, 15);
		Font fontList = new Font("Arial", Font.PLAIN, 13);

		// Creating JLabel and determinate yours features
		labelIp = new JLabel();
		labelIp.setText("Insira o IP");
		labelIp.setFont(fontGeneral);
		labelIp.setHorizontalAlignment(JLabel.CENTER);
		labelIp.setBounds(0, 20, 500, 30);

		// Creating JTextField and determinate yours features
		textIp = new JTextField();
		textIp.setFont(fontGeneral);
		textIp.setBounds(175, 60, 150, 30);

		// Creating JButton and determinate yours features
		buttonCalc = new JButton();
		buttonCalc.setFont(fontGeneral);
		buttonCalc.setText("Fornecer Dados");
		buttonCalc.setBounds(175, 120, 150, 30);

		// Creating JList
		listIp = new JList();
		listIp.setFont(fontList);

		// Creating JScrollPane and determinate yours features
		scroll = new JScrollPane(listIp);
		scroll.setBounds(07, 180, 470, 120);

		// Creating JLabel error and determinate yours features
		labelError = new JLabel();
		labelError.setFont(fontError);
		labelError.setForeground(Color.red);
		labelError.setHorizontalAlignment(JLabel.CENTER);
		labelError.setBounds(0, 325, 500, 40);
		;

		// Creating Listener of Button
		buttonCalc.addActionListener(new ActionListener() {

			// Anonymous function
			@Override
			public void actionPerformed(ActionEvent e) {

				labelError.setText("");

				Ip ip = new Ip();

				ip.setIp(textIp.getText());
				ip.extractCidr();
				ip.extractClas();

				ip.defineIpClass();

				// Printing result in JList
				String[] resultEnd = ip.vectorResult();
				listIp.setListData(resultEnd);

				// If for print in screen the message of error
				if (ip.getError().equals("x")) {
					labelError.setText("Valor digitado inválido");
				}
			}
		});

		// Add components in Screen
		container.add(textIp);
		container.add(labelIp);
		container.add(buttonCalc);
		container.add(scroll);
		container.add(labelError);

		// Turned Screen visible
		screen.setVisible(true);
	}

}
