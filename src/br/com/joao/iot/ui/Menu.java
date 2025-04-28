package br.com.joao.iot.ui;

import java.util.Scanner;

import br.com.joao.iot.model.Calculator;
import br.com.joao.iot.model.Ip;

public class Menu {

	Scanner reader = new Scanner(System.in);
	Ip ip = new Ip();
	Calculator calculator = new Calculator();
	
	
	public void showMenu() {
		
		System.out.print("Digite o Ip: ");
		ip.setIp(reader.next());
		
		ip.extractCidr();
		ip.extractClas();
		
		calculator.defineIpClass(ip);
		
		showResults();
	}
	
	
	public void showResults() {
		
		System.out.println("O Ip " + ip.getIp() + " possui as seguintes informações:");
		System.out.println("CLASSE: " + ip.getClas());
		System.out.println("MÁSCARA: " + ip.getMask());
		System.out.println("QUANTIDADES DE IP's: " + ip.getIpQuantd());
		
	}


}