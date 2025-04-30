package br.com.joao.iot.model;

public class Calculator {
	
	
	// Variable utilized for determined case have a error
	String error = "";
	
	public void defineIpClass(Ip ip) {
		
		// Variable contend the three first digits without "."
		String point = ip.getClas().replace(".", "");
		
		// Block utilized for determine if entrance of data this correct 
		try {
			
			// Variable responsible for transform of String for int  
			int clas = Integer.parseInt(point);
			
			// If and Else utilized for defined the class of IP  
			if (clas >= 1 && clas <= 127) {
				ip.setClas("A");
				
			} else if (clas >= 128 && clas <= 191) {
				ip.setClas("B");
			
			} else if (clas >= 192 && clas <= 224) {
				ip.setClas("C");
					
			} 
			
			defineMaskAndMaskBinary(ip);
			 
		} catch (Exception e) {
			
			error = "x";
		}
		
		
	}
	
	public void defineMaskAndMaskBinary(Ip ip) {
		 
		// Variable responsible for transform of String for int
		int cidr = Integer.parseInt(ip.getCidr());
		int test = cidr;
		
		// Variable contend the keys binary for determine the sub-net  
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
		
		// While utilized for extract the number multiplier of sub-net 
		while (test >= 8) {
			test-=8;
		}
		
		int cidrDec = 0;
		int begin = 0;
		int i = 0;
		
		// If and Else for define if the mask will 255 or another number 
		if (test > 0) {
			
			// While responsible for calculation of sub-mask 
			while (begin < test) {
				cidrDec += binary[i];
				i++;
				begin++;
			}
		} else {
			cidrDec = 255;
		
		}
			
		int mask255 = 255;
		String mask255Binary = Integer.toBinaryString(mask255);
		String cidrBinary = Integer.toBinaryString(cidrDec);
		
		// If's and Else's that will assemble the mask in decimal and binary completely 
		if (cidr <= 8) {
			ip.setMask(cidrDec + "." + "0" + "." + "0" + "." + "0");
			
		} else if (cidr > 8 && cidr <= 16) {
			ip.setMask(mask255 + "." + cidrDec + "." + "0" + "." + "0");
			
		} else if (cidr > 16 && cidr <= 24) {
			ip.setMask(mask255 + "." + mask255 + "." + cidrDec + "." + "0");
			
		} else if (cidr > 24 && cidr <= 32) {
			ip.setMask(mask255 + "." + mask255 + "." + mask255 + "." + cidrDec);
			
		}
		
		if (cidr <= 8) {
			ip.setMaskBinary(cidrBinary + " , " + "00000000" + " , " + "00000000" + " , " + "00000000");
			
		} else if (cidr > 8 && cidr <= 16) {
			ip.setMaskBinary(mask255Binary + " , " + cidrBinary + " , " + "00000000" + " , " + "00000000");
			
		} else if (cidr > 16 && cidr <= 24) {
			ip.setMaskBinary(mask255Binary + " , " + mask255Binary + " , " + cidrBinary + " , " + "00000000");
			
		} else if (cidr > 24 && cidr <= 32) {
			ip.setMaskBinary(mask255Binary + " , " + mask255Binary + " , " + mask255Binary + " , " + cidrBinary);
			
		}
		
		
		calculateIp(cidr , ip);
	}

	public void calculateIp(int cidr , Ip ip) {
		
		// Calculation for get the quantity of IP's available and conversation of Double for int
		double quantdDouble = (Math.pow(2, 32 - cidr));
		int quantdInt = (int)quantdDouble;
		
		ip.setIpQuantd(quantdInt);
	}
	
	public String[] vectorResult(Ip ip) {
		
		// Vector that will store all result and print in JList 
		String[] result = null;
		
		if (error.equals("")) {
			result = new String[] {
			
				"A classe do IP " + ip.getIp() + " é: " + ip.getClas(),
				"",
				"A máscara é: " + ip.getMask(),
				"",
				"A quantidade de hosts disponíveis é igual a: " + ip.getIpQuantd(),
				"",
				"A máscara deste IP em binário é: " + ip.getMaskBinary()
			};
			
		} else {
			result = new String[] {
				
				"Pareçe que for digitado algum dígito errado, por favor digite o IP novamente!"	
				
			};
		}
		
		return result;
	}

}
