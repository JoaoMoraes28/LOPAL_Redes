package br.com.joao.iot.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ip {

	// Variables of class
	private String ip;
	private String mask;
	private String maskBinary;
	private int ipQuantd;
	private String cidr;
	private String clas;

	// Variable utilized for determined case have a error
	private String error = "";

	// Method set of IP
	public String setIp(String ip) {
		
		String firstRegex = "\\.(\\d+)\\.";
		String secondRegex = "(?:\\d+\\.)(?:\\d+\\.)(\\d+)\\.";
		String thirdRegex = "(?:\\d+\\.)(?:\\d+\\.)(?:\\d+\\.)(\\d+)\\.";
		
		
		
		Pattern firstPattern = Pattern.compile(firstRegex);
		Matcher firstMatcher = firstPattern.matcher(ip);
		
		Pattern secondPattern = Pattern.compile(secondRegex);
		Matcher secondMatcher = secondPattern.matcher(ip);
		
		Pattern thirdPattern = Pattern.compile(thirdRegex);
		Matcher thirdMatcher = thirdPattern.matcher(ip);
		
		
		if (firstMatcher.find()) {
			System.out.println(firstMatcher.group(1));
		} 
		
		if (secondMatcher.find()) {
			System.out.println(secondMatcher.group(1));
		}
		
		if (thirdMatcher.find()) {
			System.out.printf("buscando...%s", thirdMatcher.group(1));
		}else {
			System.out.println("error");
		}
		this.ip = ip;
		return ip;
	}

	public String getError() {
		return error;
	}

	// Function for get the CIDR of IP
	public String extractCidr() {
		cidr = ip.substring(ip.length() - 2);
		return cidr;
	}

	// Function for get the three first character of IP and get the class
	public String extractClas() {
		clas = ip.substring(0, 3);
		return clas;
	}
	
	public void defineIpClass() {

		// Variable contend the three first digits without "."
		clas = clas.replace(".", "");

		// Block utilized for determine if entrance of data this correct
		try {

			// Variable responsible for transform of String for int
			int clasInt = Integer.parseInt(clas);

			// If and Else utilized for defined the class of IP
			if (clasInt >= 1 && clasInt <= 127) {
				clas = "A";

			} else if (clasInt >= 128 && clasInt <= 191) {
				clas = "B";

			} else if (clasInt >= 192 && clasInt <= 223) {
				clas = "C";

			} else if (clasInt >= 224 && clasInt <= 239) {
				clas = "D";

			} else if (clasInt >= 240 && clasInt <= 255) {
				clas = "E";

			}

			defineMaskAndMaskBinary();

		} catch (Exception e) {

			error = "x";
		}

	}

	public void defineMaskAndMaskBinary() {
		
		// Variable responsible for transform of String for int
		int cidrInt = Integer.parseInt(cidr);
		int test = cidrInt;

		if (cidrInt > 32) {
			error = "x";
		}

		// Variable contend the keys binary for determine the sub-net
		int[] binary = { 128, 64, 32, 16, 8, 4, 2, 1 };

		// While utilized for extract the number multiplier of sub-net
		while (test >= 8) {
			test -= 8;
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
		if (cidrInt <= 8) {
			mask = cidrDec + "." + "0" + "." + "0" + "." + "0";

		} else if (cidrInt > 8 && cidrInt <= 16) {
			mask = mask255 + "." + cidrDec + "." + "0" + "." + "0";

		} else if (cidrInt > 16 && cidrInt <= 24) {
			mask = mask255 + "." + mask255 + "." + cidrDec + "." + "0";

		} else if (cidrInt > 24 && cidrInt <= 32) {
			mask = mask255 + "." + mask255 + "." + mask255 + "." + cidrDec;

		}

		if (cidrInt <= 8) {
			maskBinary = cidrBinary + " . " + "00000000" + " . " + "00000000" + " . " + "00000000";

		} else if (cidrInt > 8 && cidrInt <= 16) {
			maskBinary = mask255Binary + " . " + cidrBinary + " . " + "00000000" + " . " + "00000000";

		} else if (cidrInt > 16 && cidrInt <= 24) {
			maskBinary = mask255Binary + " . " + mask255Binary + " . " + cidrBinary + " . " + "00000000";

		} else if (cidrInt > 24 && cidrInt <= 32) {
			maskBinary = mask255Binary + " . " + mask255Binary + " . " + mask255Binary + " . " + cidrBinary;

		}

		calculateIp(cidrInt);
	}

	public void calculateIp(int cidr) {

		// Calculation for get the quantity of IP's available and conversation of Double
		// for int
		double quantdDouble = (Math.pow(2, 32 - cidr) - 2);
		int quantdInt = (int) quantdDouble;

		if (quantdInt < 0) {
			ipQuantd = 0;
		} else {
			ipQuantd = quantdInt;
		}

	}

	public String[] vectorResult() {

		// Vector that will store all result and print in JList
		String[] result = null;

		if (error.equals("")) {
			result = new String[] {

					"A classe do IP " + ip + " é: " + clas, "", "A máscara é: " + mask, "",
					"A quantidade de hosts disponíveis é igual a: " + ipQuantd, "",
					"A máscara deste IP em binário é: " + maskBinary };

		} else {
			result = new String[] {

					""

			};
		}

		return result;
	}

}
