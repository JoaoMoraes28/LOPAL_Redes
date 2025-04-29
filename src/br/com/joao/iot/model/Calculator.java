package br.com.joao.iot.model;

import br.com.joao.iot.gui.Screen;

public class Calculator {
	
	public void defineIpClass(Ip ip) {
		
		String point = ip.getClas().replace(".", "");
		
		try {
			int clas = Integer.parseInt(point);
			
			if (clas >= 1 && clas <= 127) {
				ip.setClas("A");
				
			} else if (clas >= 128 && clas <= 191) {
				ip.setClas("B");
			
			} else if (clas >= 192 && clas <= 224) {
				ip.setClas("C");
					
			} 
			
			defineMaskBinary(ip);
			
		} catch (Exception e) {
			
			
		}
		
		
	}
	
	public void defineMaskBinary(Ip ip) {
		 
		int cidr = Integer.parseInt(ip.getCidr());
		int test = cidr;
		
		int[] binary = {
				128,
				64,
				32,
				16,
				8,
				4,
				2,
				1
		};
		
		while (test >= 8) {
			test-=8;
		}
		
		int cidrDec = 0;
		int begin = 0;
		int i = 0;
		
		if (test > 0) {
			while (begin < test) {
				cidrDec += binary[i];
				i++;
				begin++;
			}
		} else {
			cidrDec = 255;
		}
			
		int mask255 = 255;
		
		if (cidr <= 8) {
			ip.setMask(cidrDec + "." + "0" + "." + "0" + "." + "0");
			
		} else if (cidr > 8 && cidr <= 16) {
			ip.setMask(mask255 + "." + cidrDec + "." + "0" + "." + "0");
			
		} else if (cidr > 16 && cidr <= 24) {
			ip.setMask(mask255 + "." + mask255 + "." + cidrDec + "." + "0");
			
		} else if (cidr > 24 && cidr <= 32) {
			ip.setMask(mask255 + "." + mask255 + "." + mask255 + "." + cidrDec);
			
		}
		
		calculateIp(cidr , ip);
	}

	public void calculateIp(int cidr , Ip ip) {
		
		ip.setIpQuantd(Math.pow(2, 32 - cidr));
		
		
	}
	
	public String[] vectorResult(Ip ip) {
		
		String[] result = {
			
			"A classe do IP " + ip.getIp() + " é: " + ip.getClas(),
			"",
			"A máscara é: " + ip.getMask(),
			"",
			"A quantidade de hosts disponíveis é igual a: " + ip.getIpQuantd()
			
				
		};
		
		return result;
	}

}
